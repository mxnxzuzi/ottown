package controller.OTTGroupMember;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import model.domain.OTTGroupMember;
import model.domain.Consumer;
import model.domain.OTTGroup;
import model.service.OTTGroupMemberManager;
import model.service.OTTGroupManager;

public class JoinOTTGroupController implements Controller {
    private OTTGroupMemberManager ottGroupMemberManager = new OTTGroupMemberManager();
    private OTTGroupManager ottGroupManager = new OTTGroupManager();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 사용자 세션에서 userId 가져오기
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        // 요청 파라미터에서 groupId 가져오기
        int groupId = Integer.parseInt(request.getParameter("ottGroupId"));

        // OTTGroup 정보 가져오기
        OTTGroup ottGroup = ottGroupManager.findOTTGroup(groupId);
        if (ottGroup == null) {
            request.setAttribute("errorMessage", "해당 방 정보를 찾을 수 없습니다.");
            return "/errorPage.jsp";
        }

        // 최대 인원 초과 여부 확인
        if (ottGroup.getCurrentMembers() >= 4) {
            request.setAttribute("errorMessage", "공동구매방 인원이 이미 꽉 찼습니다.");
            return "/ottGroupList/view.jsp";
        }

        // Consumer 객체 설정
        Consumer consumer = new Consumer();
        consumer.setConsumerId(userId); // 세션에서 가져온 userId 설정

        // OTTGroupMember 객체 생성
        OTTGroupMember newMember = new OTTGroupMember();
        newMember.setGroup(ottGroup);
        newMember.addGroupMember(consumer);
        newMember.setPaid(0); // 기본값으로 미납 상태
        newMember.setRole(0); // 일반 멤버 역할

        // 멤버 추가 로직 수행
        boolean result = ottGroupMemberManager.addMemberToGroup(groupId, newMember);

        if (result) {
            // 성공 시 OTTGroup_member.jsp로 이동하며 방 정보 전달
            request.setAttribute("ottGroup", ottGroup);
            request.setAttribute("ottService", ottGroup.getOttService());
            request.setAttribute("account", ottGroup.getAccount()); // 계좌번호 전달
            request.setAttribute("kakaoId", ottGroup.getKakaoId()); // 카카오톡 ID 전달
            request.setAttribute("currentMembers", ottGroup.getCurrentMembers());
            return "/ottGroup/OTTGroup_member.jsp";
        } else {
            // 실패 시 오류 메시지 설정 후 방 목록 페이지로 리다이렉트
            request.setAttribute("errorMessage", "공동구매방 참여에 실패했습니다.");
            return "/ottGroupList/view.jsp";
        }
    }
}
