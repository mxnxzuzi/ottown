package controller.ottGroup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.consumer.UserSessionUtils;
import model.service.OTTGroupManager;
import model.service.RecommendationManager;
import model.domain.OTTGroup;
import model.domain.OTTService;

public class CreateOTTGroupController implements Controller {

    private OTTGroupManager ottGroupManager = new OTTGroupManager();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();

        if (!UserSessionUtils.hasLogined(session)) { return "redirect:/consumer/login"; }

        String consumerId = UserSessionUtils.getLoginUserId(session);
        
        // OTT ID 파라미터 가져오기
        String ottIdParam = request.getParameter("serviceId");

        // OTT ID 유효성 검사
        if (ottIdParam == null || ottIdParam.isEmpty()) {
            return "redirect:/OTTs/view";
        }

        int serviceId = 0;
        try {
            serviceId = Integer.parseInt(ottIdParam);
        } catch (Exception e) {
            return "redirect:/OTTs/view";
        }

        // RecommendationManager를 사용해 OTTService 객체 가져오기
        RecommendationManager recManager = RecommendationManager.getInstance();

        // 사용자 입력값 가져오기
        String kakaoId = request.getParameter("kakaoId");
        String account = request.getParameter("account");
        String ottIdInput = request.getParameter("ottId");
        String ottPw = request.getParameter("ottPassword");

        // 새로운 OTTGroup 생성
        OTTGroup newGroup = ottGroupManager.createOTTGroup(consumerId, kakaoId, account, ottIdInput, ottPw, serviceId);
        
        // 방 생성 후 OTTGroup_host.jsp로 리디렉션
        return "redirect:/ottGroup/OTTGroup_host?groupId=" + newGroup.getGroupId() + "&serviceId=" + serviceId;
    }
}