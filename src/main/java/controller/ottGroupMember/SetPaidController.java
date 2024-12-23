package controller.ottGroupMember;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.consumer.UserSessionUtils;
import model.service.OTTGroupMemberManager;

public class SetPaidController implements Controller {
    private OTTGroupMemberManager ottGroupMemberManager = new OTTGroupMemberManager();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        if (!UserSessionUtils.hasLogined(session)) { return "redirect:/consumer/login"; }

        String groupIdParam = request.getParameter("ottGroupId");
        String consumerIdParam = UserSessionUtils.getLoginUserId(session);;
        //String consumerIdParam = UserSessionUtils.getLoginUserId(session);
        String serviceIdParam = request.getParameter("serviceId");

        if (groupIdParam == null || consumerIdParam == null) {
            return "redirect:/OTTs/view";
        }

        long groupId = 0;
        long consumerId = 0;
        int serviceId = 0;

        try {
            groupId = Long.parseLong(groupIdParam);
            consumerId = Long.parseLong(consumerIdParam);
            serviceId = Integer.parseInt(serviceIdParam);
        } catch (NumberFormatException e) {
           return "redirect:/OTTs/view";
        }

        boolean result = ottGroupMemberManager.setPaid(groupId, consumerId);

        if (!result) {
            request.setAttribute("errorMessage", "입금에 실패했습니다.");
            return "/errorPage.jsp";
        }
        

        return "redirect:/mypage/ottGroup/view/group?groupId=" + groupId + "&serviceId=" + serviceId;
    }
}