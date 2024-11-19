package model.dao.content;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentDao {

    // 넷플릭스 Top10 콘텐츠 가져오기
    public List<Map<String, String>> getNetflixTop10WithImages() {
        List<Map<String, String>> netflixTop10 = new ArrayList<>();
        try {
            // URL 연결 및 HTML 파싱
            Document doc = Jsoup.connect("https://m.kinolights.com/ranking/netflix")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .timeout(5000)
                    .get();

            // 콘텐츠 항목 선택
            Elements items = doc.select("a.ranking__info"); // 각 콘텐츠 항목

            // 크롤링된 항목 수 확인
            System.out.println("크롤링된 항목 수: " + items.size());

            for (Element item : items) {
                if (netflixTop10.size() >= 10) break; // 최대 10개까지만 가져옴

                // 이미지 URL 추출
                String imageUrl = item.select(".info__poster img").attr("data-src");

                // 콘텐츠 제목 추출
                String title = item.select(".info__title").text();

                // 디버깅용 출력
                System.out.println("Title: " + title + ", Image URL: " + imageUrl);

                // 데이터를 Map에 저장
                Map<String, String> contentData = new HashMap<>();
                contentData.put("title", title);
                contentData.put("imageUrl", imageUrl);

                netflixTop10.add(contentData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return netflixTop10;
    }
}
