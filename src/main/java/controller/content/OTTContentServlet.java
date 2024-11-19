package controller.content;

import model.dao.content.ContentDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class OTTContentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ContentDao crawler = new ContentDao();

        // 넷플릭스 Top10 데이터 가져오기
        List<Map<String, String>> netflixTop10 = crawler.getNetflixTop10WithImages();

        // 디버깅: 크롤링 결과 출력
        System.out.println("Netflix Top10 크롤링 결과: " + netflixTop10);

        // 데이터를 JSP로 전달
        request.setAttribute("netflixTop10", netflixTop10);

        // JSP 페이지로 포워딩
        RequestDispatcher dispatcher = request.getRequestDispatcher("/homePage/ottTopList.jsp");
        dispatcher.forward(request, response);
    }
}
