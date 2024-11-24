
import model.dao.content.ContentDao;
import model.dto.Content;

import java.util.Date;

public class InsertTest {
    public static void main(String[] args) {
        ContentDao contentDao = new ContentDao();

        // Content 데이터 생성
        Content content = new Content();
        content.setTitle("테스트 영화1");
        content.setType("영화");
        content.setGenre("없음");
        content.setImage("test_image_url");
        content.setPublishDate(new Date());


        try {
            // 데이터 삽입
            int result = contentDao.insertContent(content);
            if (result == 1) {
                System.out.println("데이터 삽입 성공!");
            } else {
                System.out.println("데이터 삽입 실패.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
