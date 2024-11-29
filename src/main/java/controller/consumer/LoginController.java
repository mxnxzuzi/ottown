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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        System.out.println("Password : " + password);

        Consumer consumer = null;
        try {
            consumer = consumerDao.findByLoginIdAndPassword(loginId, password);
        } catch (SQLException e) {
            throw new ServletException("Database error during login", e);
        }

        if (consumer != null) {
            // 로그인 성공 처리
            System.out.println("Login successful for user: " + loginId);
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", consumer); // 사용자 정보를 세션에 저장
            request.removeAttribute("loginError");
            response.sendRedirect(request.getContextPath() + "/startpage/startpage.jsp"); // 메인 페이지로 리디렉션
        } else {
            // 로그인 실패 처리
            System.out.println("Login failed for user: " + loginId);
            request.setAttribute("loginError", true);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/loginForm/login.jsp");
            dispatcher.forward(request, response);
        }
    }

    // GET 방식 처리 (로그인 폼 페이지로 이동)
    private void handleGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/loginForm/login.jsp");
        dispatcher.forward(request, response);
    }

//    // 비밀번호 해시화 메서드 (SHA-256)
//    private String hashPassword(String password) throws ServletException {
//        try {
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] hash = digest.digest(password.getBytes());
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : hash) {
//                String hex = Integer.toHexString(0xff & b);
//                if (hex.length() == 1) {
//                    hexString.append('0');
//                }
//                hexString.append(hex);
//            }
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            throw new ServletException("Error hashing password", e);
//        }
//    }
}
