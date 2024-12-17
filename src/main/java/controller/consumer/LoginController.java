package controller.consumer;

import controller.Controller;
import model.dao.consumer.ConsumerDao;
import model.domain.Consumer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class LoginController implements Controller {
    private final ConsumerDao consumerDao = new ConsumerDao();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getMethod();

        if ("POST".equalsIgnoreCase(method)) {
            handlePost(request, response);
        } else if ("GET".equalsIgnoreCase(method)) {
            handleGet(request, response);
        }
        return null; // 응답은 메서드 내에서 처리하므로 null 반환
    }

    // POST 방식 처리 (로그인 처리)
    private void handlePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loginId = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("Login ID: " + loginId);
        System.out.println("Password: " + password);

        try {
            Consumer consumer = consumerDao.findByLoginIdAndPassword(loginId, password);
            if (consumer != null) {
                // 로그인 성공 처리
                System.out.println("로그인 성공: " + loginId);

                // 세션에 사용자 ID 저장
                HttpSession session = request.getSession();
                session.setAttribute(UserSessionUtils.USER_SESSION_KEY, consumer.getConsumerId());

                // 로그인 에러 메시지 제거
                request.removeAttribute("loginError");
                response.sendRedirect(request.getContextPath() + "/startpage/startpage.jsp"); // 메인 페이지로 리디렉션
            } else {
                // 로그인 실패 처리
                handleLoginError(request, response, loginId);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error during login", e);
        }
    }

    // GET 방식 처리 (로그인 폼 페이지로 이동)
    private void handleGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/loginForm/login.jsp");
        dispatcher.forward(request, response);
    }

    // 로그인 실패 처리
    private void handleLoginError(HttpServletRequest request, HttpServletResponse response, String loginId) throws ServletException, IOException {
        System.out.println("로그인 실패: " + loginId);
        request.setAttribute("loginError", true);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/loginForm/login.jsp");
        dispatcher.forward(request, response);
    }
}
