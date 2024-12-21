package controller.OTTGroupMember;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import model.service.OTTGroupManager;
import model.service.OTTGroupMemberManager;

public class ShareOTTInfoController implements Controller {

    private OTTGroupManager groupManager = new OTTGroupManager();
    private OTTGroupMemberManager memberManager = new OTTGroupMemberManager();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");

        // 세션에서 사용자 ID 가져오기
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customerId") == null) {
            return "redirect:/login"; // 로그인 화면으로 리디렉션
        }

        int userId = (int) session.getAttribute("customerId");
        int groupId = Integer.parseInt(request.getParameter("groupId"));

        // 권한 검증 (호스트인지 확인)
        if (!groupManager.isHostOfRoom(userId, groupId)) {
            request.setAttribute("errorMessage", "권한이 없습니다.");
            return "/error/permissionError.jsp";
        }

        // 계정 공유 활성화 (isChecked = 1)
        boolean activated = groupManager.activateOTTSharing(groupId);
        if (!activated) {
            request.setAttribute("errorMessage", "계정 공유 활성화에 실패했습니다.");
            return "/error/activationError.jsp";
        }

        // 그룹 정보 가져오기
        request.setAttribute("ottGroup", groupManager.findOTTGroup(groupId));
        request.setAttribute("ottService", groupManager.getServiceDetailsByServiceId(String.valueOf(groupId)));
        request.setAttribute("memberStatuses", memberManager.getMembersOfGroup(groupId));

        // 호스트 화면으로 이동
        return "/ottGroup/OTTGroup_host.jsp";
    }
}
