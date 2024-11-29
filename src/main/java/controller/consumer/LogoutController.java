package controller.consumer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;

public abstract class LogoutController implements Controller {

//    @Override
//    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        // 세션을 가져오기
//        HttpSession session = request.getSession(false); // 기존 세션이 있으면 반환, 없으면 null 반환
//
//        if (session != null) {
//            // 세션의 사용자 정보 삭제 (로그아웃)
//            session.invalidate();
//        }
//
//        // 로그아웃 후 리디렉션할 페이지
//        return "redirect:/loginForm/login.jsp"; // 로그인 페이지로 리디렉션
//    }
}

