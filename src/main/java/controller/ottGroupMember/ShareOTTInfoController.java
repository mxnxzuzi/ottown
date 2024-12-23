package controller.ottGroupMember;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.consumer.UserSessionUtils;
import model.domain.OTTGroup;
import model.domain.OTTService;
import model.service.OTTGroupManager;
import model.service.OTTGroupMemberManager;

import java.util.List;

public class ShareOTTInfoController implements Controller {

    private OTTGroupManager ottGroupManager = new OTTGroupManager();
    private OTTGroupMemberManager ottGroupMemberManager = new OTTGroupMemberManager();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        if (!UserSessionUtils.hasLogined(session)) { return "redirect:/consumer/login"; }

        String groupIdParam = request.getParameter("groupId");
        if (groupIdParam == null || groupIdParam.isEmpty()) {
            return "redirect:/OTTs/view";
        }

        long groupId=0;
        try {
            groupId = Long.parseLong(groupIdParam);
        } catch (NumberFormatException e) {
            return "redirect:/OTTs/view";
        }


        if (!ottGroupMemberManager.areAllMembersPaid(groupId)) {
        	return "/ottGroup/OTTGroup_member.jsp";
        }

        if (!ottGroupManager.activateOTTSharing(groupId)) {
        	return "/ottGroup/OTTGroup_member.jsp";
        }

        return "/ottGroup/OTTGroup_host.jsp";
    }
}
