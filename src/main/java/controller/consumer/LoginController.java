package controller.consumer;

import controller.Controller;
import model.domain.Consumer;
import model.service.ConsumerManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginController implements Controller {
    private final ConsumerManager consumerManager = new ConsumerManager(); // ConsumerManager 사용

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

        try {
            // ConsumerManager를 사용하여 로그인 인증
            Consumer consumer = consumerManager.authenticateConsumer(loginId, password);

            if (consumer != null) {
                // 로그인 성공 처리
//                System.out.println("로그인 성공: " + loginId);

                // 세션 객체를 요청에서 가져오기
                HttpSession session = request.getSession(true); // 세션이 없으면 새로 생성

                // 세션에 사용자 정보 저장
                session.setAttribute(UserSessionUtils.USER_SESSION_KEY, consumer.getConsumerId());
                session.setAttribute(UserSessionUtils.CONSUMER_NAME_KEY, consumer.getConsumerName());
                session.setAttribute(UserSessionUtils.CONSUMER_EMAIL_KEY, consumer.getEmail());
                session.setAttribute(UserSessionUtils.CONSUMER_PASSWORD_KEY, consumer.getPassword());

                // 세션 상태 확인 (세션 객체가 제대로 생성되었는지 확인)
//                if (session == null) {
//                    System.out.println("세션이 존재하지 않습니다.");
//                } else {
//                    System.out.println("세션 ID: " + session.getId());
//                    System.out.println("세션에 저장된 userId: " + session.getAttribute(UserSessionUtils.USER_SESSION_KEY));
//                    System.out.println("세션에 저장된 이름: " + session.getAttribute(UserSessionUtils.CONSUMER_NAME_KEY));
//                    System.out.println("세션에 저장된 이메일: " + session.getAttribute(UserSessionUtils.CONSUMER_EMAIL_KEY));
//                }

                // 로그인 에러 메시지 제거
                request.removeAttribute("loginError");
                response.sendRedirect(request.getContextPath() + "/mainpage"); // 메인 페이지로 리디렉션
            } else {
                // 로그인 실패 처리
                handleLoginError(request, response, loginId);
            }
        } catch (Exception e) {
            throw new ServletException("Login error", e);
        }
    }

    // GET 방식 처리 (로그인 폼 페이지로 이동)
    private void handleGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/loginForm/loginForm.jsp");
        dispatcher.forward(request, response);
    }

    // 로그인 실패 처리
    private void handleLoginError(HttpServletRequest request, HttpServletResponse response, String loginId) throws ServletException, IOException {
        System.out.println("로그인 실패: " + loginId);
        request.setAttribute("loginError", true);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/loginForm/loginForm.jsp");
        dispatcher.forward(request, response);
    }
}
