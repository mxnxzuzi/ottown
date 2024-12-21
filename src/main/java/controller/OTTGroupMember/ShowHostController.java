package controller.OTTGroupMember;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.dao.OTTGroup.OTTGroupDao;
import model.domain.OTTGroup;
import model.domain.OTTService;
import model.service.OTTGroupMemberManager;

import java.util.HashMap;
import java.util.Map;

public class ShowHostController implements Controller {

    private OTTGroupDao ottGroupDao = new OTTGroupDao();
    private OTTGroupMemberManager ottGroupMemberManager = new OTTGroupMemberManager();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");

        // 요청 파라미터에서 방 ID 가져오기
        String groupIdParam = request.getParameter("groupId");
        if (groupIdParam == null || groupIdParam.isEmpty()) {
            request.setAttribute("errorMessage", "방 ID가 없습니다.");
            return "/errorPage.jsp";
        }

        int groupId;
        try {
            groupId = Integer.parseInt(groupIdParam);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "잘못된 방 ID 형식입니다.");
            return "/errorPage.jsp";
        }

        // DB에서 방 정보 가져오기
        OTTGroup ottGroup = ottGroupDao.getOTTGroupById(groupId);
        if (ottGroup == null) {
            request.setAttribute("errorMessage", "유효하지 않은 방 ID입니다.");
            return "/errorPage.jsp";
        }

        // OTTService 정보 설정
        OTTService ottService = ottGroup.getOttService();
        if (ottService == null) {
            request.setAttribute("errorMessage", "OTT 서비스 정보를 찾을 수 없습니다.");
            return "/errorPage.jsp";
        }

        // 방 멤버 상태 설정
        Map<String, String> memberStatuses = new HashMap<>();
        ottGroupMemberManager.getMembersOfGroup(groupId).forEach(member -> {
            memberStatuses.put(
                member.getConsumer().getConsumerName(),
                member.isPaid() == 1 ? "입금 완료" : "입금 대기"
            );
        });

        // JSP로 데이터 전달
        request.setAttribute("ottGroup", ottGroup);
        request.setAttribute("ottService", ottService);
        request.setAttribute("memberStatuses", memberStatuses);

        return "/ottGroup/OTTGroup_host.jsp";
    }
}
