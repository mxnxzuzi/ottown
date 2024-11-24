package controller.ottservice;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import model.dto.Content;
import model.service.ContentManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContentController {
	private WebDriver driver;
	private WebDriverWait wait;
	private ContentManager contentManager = new ContentManager();

	public ContentController() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\iaki1\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
		this.driver = new ChromeDriver();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	public void crawlAndSaveMovies() {
		try {
			driver.get("https://m.kinolights.com/discover/explore");

			// 영화 필터 버튼 클릭
			WebElement subscriptionCheckbox = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='checkSubscription']")));
			subscriptionCheckbox.click();

			WebElement movieTvButton = wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//button[contains(@class, 'filter-category-btn') and contains(span/text(), '영화/TV')]")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", movieTvButton);

			WebElement movieButton = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//button[contains(@class, 'filter-item-button') and span[text()=' 영화']]")));
			movieButton.click();

			WebElement applyFilterButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.id("applyFilterButton")));
			applyFilterButton.click();
			Thread.sleep(3000);

			// 상세 페이지 URL 리스트를 저장
			List<String> detailLinks = new ArrayList<>();
			int maxMovies = 3;

			while (detailLinks.size() < maxMovies) {
				List<WebElement> movieElements = wait
						.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".MovieItem.grid")));

				for (WebElement movieElement : movieElements) {
					try {
						String relativeUrl = movieElement.findElement(By.tagName("a")).getAttribute("href");
						if (!detailLinks.contains(relativeUrl)) {
							detailLinks.add(relativeUrl);
							System.out.println("추출된 URL: " + relativeUrl);

							if (detailLinks.size() >= maxMovies) {
								break;
							}
						}
					} catch (StaleElementReferenceException e) {
						System.out.println("StaleElementReferenceException 발생, 재시도 중...");
					}
				}

				// 더 많은 데이터를 로드하기 위해 스크롤
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
				Thread.sleep(2000);
			}

			// 데이터 저장용 리스트
			List<Content> contentList = new ArrayList<>();

			// 추출된 URL로 이동하여 상세 데이터 수집
			for (String url : detailLinks) {
				driver.get(url);
				System.out.println("현재 페이지 열림: " + url);
				Thread.sleep(2000);

				try {
					// 제목
					WebElement titleElement = wait
							.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[@class='title-kr']")));
					String title = titleElement.getText().trim();
					System.out.println("제목: " + title);

					String publishDate = "정보 없음";
					try {
						// "개봉일" 항목의 텍스트를 추출
						WebElement publishDateElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
								"//li[@class='metadata__item']//span[@class='item__title' and contains(text(), '개봉일')]/following-sibling::span")));
						String fullDateText = publishDateElement.getText().trim();

						// "재개봉" 등의 부가 정보를 제거하여 개봉 날짜만 추출
						if (fullDateText.contains("재개봉")) {
							String[] dates = fullDateText.split("재개봉");
							publishDate = dates[dates.length - 1].trim(); // 마지막 항목 사용
						} else {
							publishDate = fullDateText; // 개봉일 하나만 있을 경우 그대로 사용
						}

						// "개봉"이라는 단어를 제거한 순수 날짜 추출
						if (publishDate.contains("개봉")) {
							publishDate = publishDate.replace("개봉", "").trim();
						}

					} catch (TimeoutException e) {
						System.out.println("개봉일 정보를 찾을 수 없습니다.");
						publishDate = "정보 없음"; // 기본값 설정
					}
					System.out.println("개봉일: " + publishDate);

					Date formattedDate = null;
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

					try {
					    if (!publishDate.equals("정보 없음")) {
					        formattedDate = dateFormat.parse(publishDate); // 문자열 -> Date 변환
					        System.out.println("포맷된 개봉일 (Date 객체): " + formattedDate); // Date 객체 출력
					        System.out.println("포맷된 개봉일 (포맷된 문자열): " + dateFormat.format(formattedDate)); // 다시 문자열로 출력
					    }
					} catch (ParseException e) {
					    System.out.println("날짜 변환 중 오류 발생: " + e.getMessage());
					}

					// 이미지 URL
					WebElement imageElement = wait.until(
							ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='poster']//img")));
					String imageUrl = imageElement.getAttribute("src");
					System.out.println("이미지 URL: " + imageUrl);

					// OTT 서비스
					List<WebElement> ottElements = driver.findElements(By.cssSelector(".movie-price-list .name"));
					List<String> ottServices = new ArrayList<>();
					for (WebElement ottElement : ottElements) {
						ottServices.add(ottElement.getText().trim());
					}
					System.out.println("OTT 서비스: " + ottServices);

					// Content 객체 생성 및 리스트에 추가
					Content content = new Content(0, title, "영화", "없음", imageUrl, formattedDate != null ? formattedDate : new Date());
					contentList.add(content);

				} catch (Exception e) {
					System.out.println("데이터 크롤링 중 오류 발생: " + e.getMessage());
				}
			}

			// 기존 데이터 삭제 및 저장
			contentManager.deleteAllContents();
			contentManager.saveContents(contentList);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.quit();
		}
	}
}
