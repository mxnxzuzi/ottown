package controller.OTTGroup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import model.domain.OTTGroup;
import model.domain.OTTGroupMember;
import model.service.OTTGroupMemberManager;
import model.service.OTTGroupManager;

public class ShowOTTGroupController implements Controller {

    private OTTGroupManager ottGroupManager = new OTTGroupManager();
    private OTTGroupMemberManager ottGroupMemberManager = new OTTGroupMemberManager();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");

        // 세션에서 사용자 Consumer ID 가져오기
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customerId") == null) {
            return "redirect:/login"; // 로그인 화면으로 리다이렉트
        }

        int consumerId = (int) session.getAttribute("customerId");

        // Consumer가 소속된 그룹 멤버 정보 가져오기
        OTTGroupMember member = ottGroupMemberManager.findMemberByConsumerId(consumerId);
        if (member == null) {
            // 소속된 그룹이 없는 경우 OTT 서비스 목록 페이지로 이동
            return "/OTTs/view.jsp";
        }

        // 그룹 정보 가져오기
        OTTGroup group = ottGroupManager.findOTTGroup(member.getGroup().getId());
        if (group == null) {
            // 그룹 정보가 없으면 OTT 서비스 목록으로 이동
            return "/OTTs/view.jsp";
        }

        // Consumer가 호스트인지 확인 (role == 1)
        if (member.isRole() == 1) {
            // 호스트 화면으로 이동
            request.setAttribute("ottGroup", group);
            request.setAttribute("ottService", group.getOttService());
            request.setAttribute("memberStatuses", ottGroupMemberManager.getMembersOfGroup(group.getId()));
            return "/ottGroup/OTTGroup_host.jsp";
        } else {
            // 멤버 화면으로 이동
            request.setAttribute("ottGroup", group);
            request.setAttribute("ottService", group.getOttService());
            return "/ottGroup/OTTGroup_member.jsp";
        }
    }
}
