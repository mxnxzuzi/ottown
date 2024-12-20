package controller.consumer;

import controller.Controller;
import model.service.ConsumerManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteUserController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();

        // 세션에서 로그인 상태 확인
        if (!UserSessionUtils.hasLogined(session)) {
            // 로그인하지 않은 상태라면 로그인 페이지로 이동
            request.setAttribute("error", "You must be logged in to delete your account.");
            System.out.println("로그인 인식 실패");
            return "/consumer/login";
        }

        // 세션에서 현재 로그인한 사용자 ID 가져오기
        String loginUserId = UserSessionUtils.getLoginUserId(session);
        if (loginUserId == null) {
            request.setAttribute("error", "Unable to retrieve user information.");
            System.out.println("ID 가져오기 실패.");
            return "/consumer/mypage";
        }

        // ConsumerManager 객체 생성
        ConsumerManager consumerManager = new ConsumerManager();

        try {
            long consumerId = Long.parseLong(loginUserId); // 세션의 사용자 ID를 Long으로 변환
            System.out.println("ID : " + consumerId);

            // 회원 삭제 수행
            boolean isDeleted = consumerManager.deleteConsumer(consumerId);

            if (isDeleted) {
                // 회원 삭제 성공 시 세션 무효화 및 성공 페이지로 이동
                session.invalidate(); // 세션 종료
                request.setAttribute("message", "Your account has been successfully deleted.");
                System.out.println("회원 탈퇴 성공");
                return "/mainpage";
            } else {
                // 실패 시 에러 메시지와 함께 마이페이지로 이동
                request.setAttribute("error", "Failed to delete your account. Please try again.");
                System.out.println("회원 탈퇴 실패");
                return "/consumer/mypage";
            }
        } catch (NumberFormatException e) {
            // 세션의 사용자 ID가 올바르지 않을 경우 처리
            request.setAttribute("error", "Invalid user ID.");
            System.out.println("ID 불일치");
            return "/consumer/mypage";
        }
    }
}
