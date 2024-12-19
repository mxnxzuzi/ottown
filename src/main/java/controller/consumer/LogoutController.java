package controller.consumer;

import controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        System.out.println("로그아웃 성공");
        response.sendRedirect(request.getContextPath() + "/mainpage"); // 로그아웃 후 메인 페이지로 리디렉션
        return null;
    }
}
