package controller.OTTGroupMember;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.service.OTTGroupMemberManager;

public class KickMemberController implements Controller {

    private OTTGroupMemberManager ottGroupMemberManager = new OTTGroupMemberManager();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");


        int ottGroupMemberId = Integer.parseInt(request.getParameter("ottGroupMemberId"));
        int ottGroupId = Integer.parseInt(request.getParameter("ottGroupId"));

        // 멤버 강퇴
        boolean result = ottGroupMemberManager.removeMemberFromGroup(ottGroupId, ottGroupMemberId);

        if (!result) {
            request.setAttribute("errorMessage", "멤버 퇴장에 실패했습니다.");
            return "/errorPage.jsp";
        }

        // 성공 시 리디렉션
        return "redirect:/ottGroup/host?groupId=" + ottGroupId;
    }
}
