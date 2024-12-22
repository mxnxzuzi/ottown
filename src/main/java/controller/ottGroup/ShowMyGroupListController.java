package controller.ottGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.consumer.UserSessionUtils;
import model.dao.OTTGroup.OTTGroupDao;
import model.dao.consumer.ConsumerDao;
import model.domain.Consumer;
import model.domain.OTTGroup;
import model.domain.OTTService;
import model.service.OTTGroupManager;
import model.service.OTTGroupMemberManager;

public class ShowMyGroupListController implements Controller {
    private OTTGroupDao ottGroupDao = new OTTGroupDao();
    private ConsumerDao consumerDao = new ConsumerDao();
    private OTTGroupManager ottGroupManager = new OTTGroupManager();
    private OTTGroupMemberManager ottGroupMemberManager = new OTTGroupMemberManager();  // Manager 클래스

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        if (!UserSessionUtils.hasLogined(session)) { return "redirect:/consumer/login"; }
        String consumerId = UserSessionUtils.getLoginUserId(session);

        // DAO를 통해 OTT 그룹 목록 가져오기
        List<OTTGroup> groupList = ottGroupManager.getOTTGroupsByConsumerId(consumerId);

        // 각 그룹의 호스트 이름 추가
        Map<Long, String> hostNames = new HashMap<>();
        for (OTTGroup group : groupList) {
            try {
                // groupId로 role = true인 멤버의 consumerId 찾기
                Long userId = ottGroupMemberManager.getHost(group.getGroupId()).getConsumerId();
                if (userId != null) {
                    // consumerId로 Consumer 정보 조회
                    Consumer consumer = consumerDao.getConsumerById(userId);
                    if (consumer != null) {
                        hostNames.put(group.getGroupId(), consumer.getConsumerName());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(); // 오류 처리 (호스트 정보가 없는 경우)
                hostNames.put(group.getGroupId(), "알 수 없음");
            }
        }
        
        Map<Long, OTTService> ottServiceList = new HashMap<>();
        for (OTTGroup group : groupList) {
            try {
             // ottId로 OTTService 찾기
                OTTService ottService = null;
                for (OTTService service : OTTService.values()) {
                    if (service.getId() == group.getServiceId()) {
                        ottService = service;
                        break;
                    }
                }
                
                ottServiceList.put(group.getGroupId(), ottService);
            } catch (Exception e) {
                e.printStackTrace(); // 오류 처리 (호스트 정보가 없는 경우)
                hostNames.put(group.getGroupId(), "알 수 없음");
            }
        }

        // JSP로 데이터 전달
        request.setAttribute("ottGroupList", groupList);
        request.setAttribute("ottServiceList", ottServiceList);
        request.setAttribute("hostNames", hostNames); // 그룹별 호스트 이름 전달
        
        return "/ottGroup/showMyGroupList.jsp";
    }
}