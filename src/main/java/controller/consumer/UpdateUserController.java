package controller.consumer;

import controller.Controller;
import model.service.ConsumerManager;
import model.domain.Consumer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateUserController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 세션에서 현재 로그인한 사용자의 ID 가져오기
        HttpSession session = request.getSession();
        String consumerId = UserSessionUtils.getLoginUserId(session);

        if (consumerId == null) {
            // 로그인되지 않은 사용자 처리
            request.setAttribute("error", "로그인 후에 회원 정보를 수정할 수 있습니다.");
            return "/consumer/login";  // 로그인 페이지로 이동
        }

        // 폼에서 수정된 회원 정보 가져오기
        String newConsumerName = request.getParameter("consumerName");
        String newConsumerEmail = request.getParameter("consumerEmail");
        String newConsumerPassword = request.getParameter("consumerPassword");

        System.out.println(newConsumerName + newConsumerEmail + newConsumerPassword);

        // ConsumerManager를 사용하여 정보 수정
        ConsumerManager consumerManager = new ConsumerManager();
        boolean updateSuccess = consumerManager.updateConsumerInfo(
                Long.parseLong(consumerId),  // consumerId를 파싱하여 넘김
                newConsumerName,              // 새 이름
                newConsumerEmail,            // 새 이메일
                newConsumerPassword         // 새 비밀번호
        );

        System.out.println(updateSuccess);

        if (updateSuccess) {
            // 수정된 정보를 세션에 다시 저장
            UserSessionUtils.setConsumerInfo(session, newConsumerName, newConsumerEmail, newConsumerPassword);

            // 수정 완료 메시지
            request.setAttribute("message", "회원 정보가 성공적으로 수정되었습니다.");
            return "redirect:/consumer/mypage";  // 마이페이지로 리다이렉트
        } else {
            // 실패 시, 오류 메시지
            request.setAttribute("error", "회원 정보 수정에 실패했습니다.");
            return "/error.jsp";  // 실패 페이지로 이동
        }
    }
}
