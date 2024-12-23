package controller.ottGroupMember;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.consumer.UserSessionUtils;
import model.service.OTTGroupMemberManager;

public class KickMemberController implements Controller {

    private OTTGroupMemberManager ottGroupMemberManager = new OTTGroupMemberManager();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        if (!UserSessionUtils.hasLogined(session)) { return "redirect:/consumer/login"; }

        String groupIdParam = request.getParameter("groupId");
        String consumerIdParam = request.getParameter("consumerId");
        String serviceIdParam = request.getParameter("serviceId");
        
        if (groupIdParam == null || consumerIdParam == null) {
            return "redirect:/OTTs/view";
        }

        long groupId = 0;
        long consumerId = 0;
        long serviceId = 0;
        

        try {
            groupId = Long.parseLong(groupIdParam);
            consumerId = Long.parseLong(consumerIdParam);
            serviceId = Long.parseLong(serviceIdParam);
        } catch (NumberFormatException e) {
           return "redirect:/OTTs/view";
        }

        // 멤버 강퇴
        boolean result = ottGroupMemberManager.removeOTTGroupMember(groupId, consumerId) > 0;

        if (!result) {
            return "redirect:/ottGroup/OTTGroup_host?groupId=" + groupId + "&serviceId=" + serviceId;
        }

        // 성공 시 리디렉션
        return "redirect:/ottGroup/OTTGroup_host?groupId=" + groupId + "&serviceId=" + serviceId;
    }
}