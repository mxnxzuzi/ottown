package controller.ottGroup;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.consumer.UserSessionUtils;
import model.dao.OTTGroup.OTTGroupDao;
import model.domain.OTTGroup;
import model.domain.OTTGroupMember;
import model.domain.OTTService;
import model.service.OTTGroupManager;
import model.service.OTTGroupMemberManager;
import model.service.RecommendationManager;

public class ShowHostController implements Controller {

   private OTTGroupDao ottGroupDao = new OTTGroupDao();
    private OTTGroupMemberManager ottGroupMemberManager = new OTTGroupMemberManager();
    private RecommendationManager recManager = RecommendationManager.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        if (!UserSessionUtils.hasLogined(session)) { return "redirect:/consumer/login"; }
        
        // groupId 및 serviceId 파라미터 가져오기
        String groupIdParam = request.getParameter("groupId");
        String serviceIdParam = request.getParameter("serviceId");

        // 파라미터 유효성 검사
        if (groupIdParam == null || serviceIdParam == null) {
           return "redirect:/OTTs/view";
        }

        long groupId = 0;
        int serviceId = 0;

        try {
            groupId = Long.parseLong(groupIdParam);
            serviceId = Integer.parseInt(serviceIdParam);
        } catch (NumberFormatException e) {
           return "redirect:/OTTs/view";
        }

        // OTTGroup 정보 조회
        OTTGroup ottGroup = ottGroupDao.getOTTGroupById(groupId);
        if (ottGroup == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "해당 그룹을 찾을 수 없습니다.");
            return null;
        }
        
        List<OTTGroupMember> ottGroupMembers = ottGroupMemberManager.getMembersByGroupId(groupId);
        
        
        // RecommendationManager를 사용해 OTTService 객체 가져오기
        RecommendationManager recManager = RecommendationManager.getInstance();

     // ottId로 OTTService 찾기
        OTTService ottService = null;
        for (OTTService service : OTTService.values()) {
            if (service.getId() == serviceId) {
                ottService = service;
                break;
            }
        }

        String host = ottGroupMemberManager.getHostName(groupId);

        // JSP로 데이터 전달
        request.setAttribute("ottGroup", ottGroup);
        request.setAttribute("ottGroupMembers", ottGroupMembers);
        request.setAttribute("ottService", ottService);
        request.setAttribute("host", host);

        return "/ottGroup/OTTGroup_host.jsp";
    }
}
