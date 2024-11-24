package controller.ottservice;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import model.dto.Content;
import model.service.ContentManager;

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
			WebElement movieTvButton = wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//button[contains(@class, 'filter-category-btn') and contains(span/text(), '영화/TV')]")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", movieTvButton);

			WebElement movieButton = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//button[contains(@class, 'filter-item-button') and span[text()=' 영화']]")));
			movieButton.click();

			WebElement applyFilterButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.id("applyFilterButton")));
			applyFilterButton.click();

			WebElement subscriptionCheckbox = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='checkSubscription']")));
			subscriptionCheckbox.click();

			// 영화 데이터를 가져오기
			List<String> movieData = new ArrayList<>();
			int maxMovies = 100;

			while (movieData.size() < maxMovies) {
				List<WebElement> movieElements = wait
						.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".MovieItem.grid")));

				for (WebElement movieElement : movieElements) {
					try {
						String title = movieElement.findElement(By.className("title")).getText();
						String imageUrl = movieElement.findElement(By.tagName("img")).getAttribute("src");

						String movieInfo = "영화: " + title + ", 이미지: " + imageUrl;
						if (!movieData.contains(movieInfo)) {
							movieData.add(movieInfo);
							System.out.println(movieInfo);

							if (movieData.size() >= maxMovies)
								break;
						}
					} catch (StaleElementReferenceException e) {
						System.out.println("StaleElementReferenceException 발생, 재시도 중...");
					}
				}
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
				Thread.sleep(2000);
			}

			List<Content> contentList = new ArrayList<>();
			for (String movieInfo : movieData) {
				String[] parts = movieInfo.split(", 이미지: ");
				String title = parts[0].replace("영화: ", "").trim();
				String imageUrl = parts[1].trim();

				Content content = new Content(0, title, "영화", "없음", imageUrl, new Date());
				contentList.add(content);
			}

			// 기존 데이터 삭제
			contentManager.deleteAllContents();
			
			// 데이터 저장
			contentManager.saveContents(contentList);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// driver.quit();
		}
	}
}