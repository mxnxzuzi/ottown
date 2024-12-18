package util;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CrawlingUtil {

    public static String crawlOTTData(String ottService) {
        StringBuilder htmlOutput = new StringBuilder();
        String url = "https://m.kinolights.com/ranking/" + ottService;

        //System.setProperty("webdriver.chrome.silentOutput", "true");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.SEVERE);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(url);

            // 요소 대기 설정
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> rankingItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("a.ranking__info")));

            System.out.println("크롤링된 항목 수: " + rankingItems.size());

            for (int i = 0; i < Math.min(10, rankingItems.size()); i++) {
                WebElement item = rankingItems.get(i);

                // 이미지 URL 및 제목 가져오기
                WebElement imgElement = item.findElement(By.cssSelector("div.info__poster img"));
                String imgSrc = imgElement.getAttribute("data-src");
                if (imgSrc == null || imgSrc.isEmpty()) {
                    imgSrc = imgElement.getAttribute("src");
                }

                String title = imgElement.getAttribute("alt");

                // HTML 생성
                htmlOutput.append("<div class='content-item'>")
                          .append("<img src='").append(imgSrc).append("' alt='").append(title).append("' />")
                          .append("<div class='number-overlay'>").append(i + 1).append("</div>")
                          .append("<h3 class='content-title'>").append(title).append("</h3>")
                          .append("</div>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "<div>데이터를 불러올 수 없습니다.</div>";
        } finally {
            driver.quit();
        }

        return htmlOutput.toString();
    }
}