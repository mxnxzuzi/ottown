import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;

public class test {
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\iaki1\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // 대기 시간 증가

		try {
			driver.get("https://m.kinolights.com/discover/explore");

			// 요소 대기 및 JavaScript로 클릭
			WebElement movieTvButton = wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//button[contains(@class, 'filter-category-btn') and contains(span/text(), '영화/TV')]")));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", movieTvButton);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", movieTvButton);

			System.out.println("영화/TV 버튼 클릭 완료!");

			WebElement movieButton = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//button[contains(@class, 'filter-item-button') and span[text()=' 영화']]")));

			// "영화" 버튼 클릭
			movieButton.click();
			System.out.println("영화 버튼이 클릭되었습니다.");

			WebElement applyFilterButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.id("applyFilterButton")));

			applyFilterButton.click();
			System.out.println("작품보기 버튼이 클릭되었습니다.");

			WebElement subscriptionCheckbox = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='checkSubscription']")));

			// "정액제만 보기" 체크박스 클릭
			subscriptionCheckbox.click();
			System.out.println("정액제만 보기 체크박스가 선택되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// driver.quit();
		}
	}
}
