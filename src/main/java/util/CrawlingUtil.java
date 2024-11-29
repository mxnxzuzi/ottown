package util;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.*;

public class CrawlingUtil {
    public static String crawlOTTData(String ottService) {
        StringBuilder htmlOutput = new StringBuilder();
        String url = "https://m.kinolights.com/ranking/" + ottService;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements rankingList = doc.select("a[id=contentDailyRankingList]");
            int count = 0;

            for (Element ranking : rankingList) {
                if (count >= 10) break;

                // 제목 크롤링
                String title = ranking.select("p.info__title").text();

                // 이미지 URL 크롤링
                String imgSrc = extractImageSrc(ranking.select("div.info__poster img"));

                // 기본 이미지 설정 (이미지가 유효하지 않을 경우 사용)
                if (imgSrc == null || imgSrc.isEmpty() || isImageAccessDenied(imgSrc)) {
                    imgSrc = "/images/movie.png"; // 기본 이미지 경로
                }

                // 결과 HTML 생성
                htmlOutput.append("<div class='content-item'>");
                htmlOutput.append("<img src='" + imgSrc + "' alt='poster'/>");
                htmlOutput.append("<div class='number-overlay'>" + (count + 1) + "</div>");
                htmlOutput.append("<h3 class='content-title'>" + title + "</h3>");
                htmlOutput.append("</div>");
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return htmlOutput.toString();
    }

    private static String extractImageSrc(Elements imgElements) {
        String imgSrc = "";

        // 우선순위별로 이미지 URL 추출
        if (imgElements.attr("data-src") != null && !imgElements.attr("data-src").isEmpty()) {
            imgSrc = imgElements.attr("data-src");
        } else if (imgElements.attr("src") != null && !imgElements.attr("src").isEmpty()) {
            imgSrc = imgElements.attr("src");
        } else if (imgElements.attr("data-srcset") != null && !imgElements.attr("data-srcset").isEmpty()) {
            imgSrc = imgElements.attr("data-srcset").split(",")[0].trim();
        } else if (imgElements.attr("data-original") != null && !imgElements.attr("data-original").isEmpty()) {
            imgSrc = imgElements.attr("data-original");
        } else if (imgElements.attr("data-lazy") != null && !imgElements.attr("data-lazy").isEmpty()) {
            imgSrc = imgElements.attr("data-lazy");
        }

        // 절대 경로로 변환 (만약 상대 경로인 경우)
        if (imgSrc != null && !imgSrc.startsWith("http")) {
            imgSrc = "https://m.kinolights.com" + imgSrc;
        }

        return imgSrc;
    }

    private static boolean isImageAccessDenied(String imgSrc) {
        try {
            Connection.Response response = Jsoup.connect(imgSrc).ignoreContentType(true).execute();
            return response.statusCode() != 200; // 200이 아닌 상태 코드는 접근 실패로 간주
        } catch (IOException e) {
            return true; // 예외 발생 시 접근 불가로 간주
        }
    }
}