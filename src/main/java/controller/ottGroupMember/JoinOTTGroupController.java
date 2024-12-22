package controller.ottGroupMember;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.consumer.UserSessionUtils;
import model.domain.Consumer;
import model.domain.OTTGroup;
import model.domain.OTTGroupMember;
import model.domain.OTTService;
import model.service.OTTGroupManager;
import model.service.OTTGroupMemberManager;

public class JoinOTTGroupController implements Controller {
    
    private OTTGroupMemberManager ottGroupMemberManager = new OTTGroupMemberManager();
    private OTTGroupManager ottGroupManager = new OTTGroupManager();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 사용자 세션에서 userId 가져오기
        HttpSession session = request.getSession();

        if (!UserSessionUtils.hasLogined(session)) { return "redirect:/consumer/login"; }

        String consumerId = UserSessionUtils.getLoginUserId(session);

        // 요청 파라미터에서 groupId 가져오기
        String groupId = request.getParameter("groupId");
        String serviceId = request.getParameter("serviceId");

        long consumerIdL = Long.parseLong(consumerId);
        long groupIdL = Long.parseLong(groupId);

        boolean t = ottGroupManager.joinGroup(consumerIdL, groupIdL);
        
        // OTTGroup 정보 가져오기
        OTTGroup ottGroup = ottGroupManager.getOTTGroupById(groupId);
        if (ottGroup == null) {
            return "redirect:/ottGroupList/OTTGroupList?ottId=" + serviceId;
        }

     // 최대 인원 초과 여부 확인
        if (ottGroup.getCurrentMembers() >= 4) {
            request.setAttribute("errorMessage", "공동구매방 인원이 이미 꽉 찼습니다.");
            return "redirect:/ottGroupList/OTTGroupList?ottId=" + serviceId;
        }

        int result = 0;
        if (groupId != null) {
            result = ottGroupMemberManager.addOTTGroupMember(Long.parseLong(groupId), Long.parseLong(consumerId));
        }
        
     // ottId로 OTTService 찾기
        OTTService ottService = null;
        for (OTTService service : OTTService.values()) {
            if (service.getId() == Integer.parseInt(serviceId)) {
                ottService = service;
                break;
            }
        }

        if (result != 0) {
            // 성공 시 OTTGroup_member.jsp로 이동하며 방 정보 전달
            request.setAttribute("ottGroup", ottGroup);
            request.setAttribute("ottService", ottService);
            request.setAttribute("account", ottGroup.getAccount()); // 계좌번호 전달
            request.setAttribute("kakaoId", ottGroup.getKakaoId()); // 카카오톡 ID 전달
            request.setAttribute("currentMembers", ottGroup.getCurrentMembers());
            return "/ottGroup/OTTGroup_member.jsp";
        } else {
            // 실패 시 오류 메시지 설정 후 방 목록 페이지로 리다이렉트
            return "redirect:/ottGroupList/OTTGroupList?ottId=" + serviceId;
        }
    }

}
