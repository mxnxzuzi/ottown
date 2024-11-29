package controller.consumer;

import controller.Controller;
import model.dao.consumer.ConsumerDao;
import model.domain.Consumer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class SignUpController implements Controller {
    private final ConsumerDao consumerDao = new ConsumerDao();

    // execute 메서드로 GET과 POST 처리를 분리
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

    // POST 방식 처리 (회원가입 처리)
    private void handlePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // Consumer 객체 생성하여 값 세팅
        Consumer consumer = new Consumer();
        consumer.setConsumerName(userName);
        consumer.setPassword(password);
        consumer.setEmail(email);

        boolean isSuccess = false;
        try {
            isSuccess = consumerDao.create(consumer);
        } catch (SQLException e) {
            throw new ServletException("Database error during user registration", e);
        }

        if (isSuccess) {
            // 회원가입 성공 시 로그인 페이지로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/loginForm/login.jsp");
        } else {
            // 실패 시 회원가입 폼으로 돌아가도록 처리 (포워드 사용)
            request.setAttribute("signUpError", true);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/signupForm/signup.jsp");
            dispatcher.forward(request, response);
        }
    }

    // GET 방식 처리 (회원가입 폼 페이지로 이동)
    private void handleGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/signupForm/signup.jsp");
        dispatcher.forward(request, response);
    }
}