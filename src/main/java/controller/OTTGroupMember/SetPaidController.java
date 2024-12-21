package controller.OTTGroupMember;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.service.OTTGroupMemberManager;
import model.service.OTTGroupManager;

public class SetPaidController implements Controller {

    private OTTGroupMemberManager ottGroupMemberManager = new OTTGroupMemberManager();
    private OTTGroupManager ottGroupManager = new OTTGroupManager();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");

        int ottGroupMemberId = Integer.parseInt(request.getParameter("ottGroupMemberId"));
        int ottGroupId = Integer.parseInt(request.getParameter("ottGroupId"));

        // 1. 멤버의 isPaid를 1로 업데이트
        boolean paid = ottGroupMemberManager.setPaid(ottGroupMemberId, ottGroupId);

        if (!paid) {
            request.setAttribute("errorMessage", "입금 확인에 실패했습니다.");
            return "/ottGroup/OTTGroup_member.jsp"; // 실패 시 바로 JSP로 이동
        }

        // 2. 현재 방의 멤버 수 확인
        int currentMembers = ottGroupMemberManager.countMembersInGroup(ottGroupId);

        // 3. 모든 멤버의 isPaid 상태 확인 (currentMembers가 4명일 경우만)
        if (currentMembers == 4) {
            boolean allPaid = ottGroupMemberManager.areAllMembersPaid(ottGroupId);

            // 4. 모든 멤버가 입금을 완료했으면 그룹 상태를 업데이트
            if (allPaid) {
                ottGroupManager.updateGroupStatus(ottGroupId);
            }
        }

        // 5. JSP로 필요한 데이터 설정
        request.setAttribute("ottGroup", ottGroupManager.findOTTGroup(ottGroupId));
        request.setAttribute("ottService", ottGroupManager.getServiceDetailsByServiceId(String.valueOf(ottGroupId)));
        request.setAttribute("memberStatuses", ottGroupMemberManager.getMembersOfGroup(ottGroupId));

        // 6. OTTGroup_member.jsp로 이동
        return "/ottGroup/OTTGroup_member.jsp";
    }
}
