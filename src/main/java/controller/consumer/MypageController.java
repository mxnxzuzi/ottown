package controller.consumer;

import controller.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MypageController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("consumerName") == null) {
            // 세션이 없거나 로그인되지 않은 상태이면 로그인 페이지로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/consumer/login");
            return null;
        }

        // 세션에서 사용자 정보를 가져와 JSP로 전달
        String consumerName = (String) session.getAttribute("consumerName");
        String consumerEmail = (String) session.getAttribute("consumerEmail");
        String password = (String) session.getAttribute("password");

        request.setAttribute("consumerName", consumerName);
        request.setAttribute("consumerEmail", consumerEmail);
        request.setAttribute("password", password);

        try {
            request.getRequestDispatcher("/WEB-INF/mypage/mypage.jsp").forward(request, response); // JSP로 포워딩
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
