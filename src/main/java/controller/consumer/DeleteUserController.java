package controller.consumer;

import controller.Controller;
import model.service.ConsumerManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteUserController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false); // 세션 가져오기 (없으면 null 반환)

        // 세션 확인 및 로그인 여부 검사
        if (session == null || !UserSessionUtils.hasLogined(session)) {
            request.setAttribute("error", "로그인이 필요합니다.");
            System.out.println("로그인하지 않은 사용자");
            return "/consumer/login";
        }

        // 세션에서 사용자 ID 가져오기
        String loginUserId = UserSessionUtils.getLoginUserId(session);
        if (loginUserId == null) {
            request.setAttribute("error", "사용자 정보를 확인할 수 없습니다.");
            System.out.println("세션에서 사용자 ID를 가져오지 못했습니다.");
            return "/consumer/mypage";
        }

        ConsumerManager consumerManager = new ConsumerManager();

        try {
            long consumerId = Long.parseLong(loginUserId); // 사용자 ID 변환
            System.out.println("탈퇴 요청 ID: " + consumerId);

            // ConsumerManager를 통해 회원 탈퇴 처리
            boolean isDeleted = consumerManager.deleteConsumer(consumerId);

            if (isDeleted) {
                // 탈퇴 성공: 세션 무효화
                session.invalidate(); // 세션을 무효화하여 로그아웃 처리
                request.setAttribute("message", "회원 탈퇴가 성공적으로 처리되었습니다.");
                System.out.println("회원 탈퇴 성공");

                // 성공 시 /mainpage로 리다이렉트
                response.sendRedirect(request.getContextPath() + "/mainpage");
                return null; // sendRedirect 사용 시 return null로 처리
            } else {
                // 탈퇴 실패: 에러 메시지와 함께 마이페이지로 이동
                request.setAttribute("error", "회원 탈퇴에 실패했습니다. 다시 시도해주세요.");
                System.out.println("회원 탈퇴 실패");
                return "/consumer/mypage";
            }
        } catch (NumberFormatException e) {
            // ID 변환 실패 처리
            request.setAttribute("error", "올바르지 않은 사용자 ID입니다.");
            System.out.println("사용자 ID 형식 오류");
            return "/consumer/mypage";
        } catch (Exception e) {
            // 기타 예외 처리
            request.setAttribute("error", "회원 탈퇴 처리 중 문제가 발생했습니다.");
            System.err.println("회원 탈퇴 처리 중 오류: " + e.getMessage());
            return "/consumer/mypage";
        }
    }
}