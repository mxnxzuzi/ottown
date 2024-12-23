package controller.ottGroup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.Controller;
import model.dao.OTTGroup.OTTGroupDao;
import model.dao.consumer.ConsumerDao;
import model.domain.OTTService;
import model.domain.OTTGroup;
import model.domain.Consumer;
import model.service.OTTGroupMemberManager;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ShowOTTGroupListController implements Controller {
    private OTTGroupDao ottGroupDao = new OTTGroupDao();
    private ConsumerDao consumerDao = new ConsumerDao();
    private OTTGroupMemberManager ottGroupMemberManager = new OTTGroupMemberManager();  // Manager 클래스

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");

        // 요청 파라미터에서 ottId 가져오기
        String ottIdParam = request.getParameter("ottId");

        // ottId가 없거나 유효하지 않은 경우 처리
        if (ottIdParam == null || ottIdParam.isEmpty()) {
            request.setAttribute("errorMessage", "OTT ID가 없습니다.");
            return "redirect:/OTTs/view";
        }

        int ottId;
        try {
            ottId = Integer.parseInt(ottIdParam);
        } catch (NumberFormatException e) {
            return "redirect:/OTTs/view";
        }

        // ottId로 OTTService 찾기
        OTTService ottService = null;
        for (OTTService service : OTTService.values()) {
            if (service.getId() == ottId) {
                ottService = service;
                break;
            }
        }

        if (ottService == null) {
            return "redirect:/OTTs/view";
        }

        // DAO를 통해 OTT 그룹 목록 가져오기
        List<OTTGroup> groupList = ottGroupDao.getOTTGroupsByServiceId(ottService.getId());

        // 각 그룹의 호스트 이름 추가
        Map<Long, String> hostNames = new HashMap<>();
        for (OTTGroup group : groupList) {
            try {
                // groupId로 role = true인 멤버의 consumerId 찾기
                Long consumerId = ottGroupMemberManager.getHost(group.getGroupId()).getConsumerId();
                if (consumerId != null) {
                    // consumerId로 Consumer 정보 조회
                    Consumer consumer = consumerDao.getConsumerById(consumerId);
                    if (consumer != null) {
                        hostNames.put(group.getGroupId(), consumer.getConsumerName());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(); // 오류 처리 (호스트 정보가 없는 경우)
                hostNames.put(group.getGroupId(), "알 수 없음");
            }
        }

        // JSP로 데이터 전달
        request.setAttribute("ottGroupList", groupList);
        request.setAttribute("ottService", ottService);
        request.setAttribute("hostNames", hostNames); // 그룹별 호스트 이름 전달

        return "/ottGroupList/OTTGroupList.jsp";  // 데이터가 전달된 후 JSP로 이동
    }
}