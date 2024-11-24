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
                // info_title 크롤링
                String title = ranking.select("p.info__title").text();
                
                // info_poster 크롤링 - 가능한 모든 속성 시도
                String imgSrc = ranking.select("div.info__poster img").attr("data-src");
                if (imgSrc == null || imgSrc.isEmpty()) {
                    imgSrc = ranking.select("div.info__poster img").attr("src");
                }
                if (imgSrc == null || imgSrc.isEmpty()) {
                    String srcset = ranking.select("div.info__poster img").attr("data-srcset");
                    if (srcset != null && !srcset.isEmpty()) {
                        imgSrc = srcset.split(",")[0].trim();
                    }
                }
                if (imgSrc == null || imgSrc.isEmpty()) {
                    imgSrc = ranking.select("div.info__poster img").attr("data-original");
                }
                if (imgSrc == null || imgSrc.isEmpty()) {
                    imgSrc = ranking.select("div.info__poster img").attr("data-lazy");
                }
                
                // 절대 경로로 변환 (만약 상대 경로인 경우)
                if (imgSrc != null && !imgSrc.startsWith("http")) {
                    imgSrc = "https://m.kinolights.com" + imgSrc;
                }
                
                // 결과 출력
                htmlOutput.append("<div class='content-item'>");
                if (imgSrc != null && !imgSrc.isEmpty()) {
                    htmlOutput.append("<img src='" + imgSrc + "' alt='poster'/>");
                } else {
                    htmlOutput.append("<p>이미지 없음</p>");
                }
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
}