package controller.ottGroup;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.consumer.UserSessionUtils;
import model.domain.OTTGroup;
import model.domain.OTTGroupMember;
import model.domain.OTTService;
import model.service.OTTGroupManager;
import model.service.OTTGroupMemberManager;
import model.service.RecommendationManager;

public class PassToOTTGroupController implements Controller {
   private OTTGroupManager ottGroupManager = new OTTGroupManager();
   private OTTGroupMemberManager ottGroupMemberManager = new OTTGroupMemberManager();
   

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
     
        HttpSession session = request.getSession();
        if (!UserSessionUtils.hasLogined(session)) { return "redirect:/consumer/login"; }

        String consumerId = UserSessionUtils.getLoginUserId(session);

        String groupIdParam = request.getParameter("groupId");
        
        if (groupIdParam == null) {
            return "redirect:/OTTs/view";
         }

         long groupId = 0;

         try {
             groupId = Long.parseLong(groupIdParam);
         } catch (NumberFormatException e) {
            return "redirect:/OTTs/view";
         }
        
         OTTGroupMember member = ottGroupMemberManager.getMemberById(groupId, Long.parseLong(consumerId));
         
         // OTTGroup 정보 조회
         OTTGroup ottGroup = ottGroupManager.getOTTGroupById(String.valueOf( groupId));
         if (ottGroup == null) {
             response.sendError(HttpServletResponse.SC_NOT_FOUND, "해당 그룹을 찾을 수 없습니다.");
             return null;
         }
         
         List<OTTGroupMember> ottGroupMembers = ottGroupMemberManager.getMembersByGroupId(groupId);
         
         
         // RecommendationManager를 사용해 OTTService 객체 가져오기
         RecommendationManager recManager = RecommendationManager.getInstance();
         int serviceId = Integer.parseInt(request.getParameter("serviceId"));

         // ottId로 OTTService 찾기
         OTTService ottService = null;
         for (OTTService service : OTTService.values()) {
             if (service.getId() == serviceId) {
                 ottService = service;
                 break;
             }
         }

         String host = ottGroupMemberManager.getHostName(groupId);

         
         request.setAttribute("ottGroup", ottGroup);
         request.setAttribute("ottGroupMembers", ottGroupMembers);
         request.setAttribute("ottService", ottService);
         request.setAttribute("host", host);
         
         if(member.getRole() == 1) {
            return "/ottGroup/OTTGroup_host.jsp";
         }
         else {
            return "/ottGroup/OTTGroup_member.jsp";
         }
    }
}
