package controller.OTTGroup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import model.service.OTTGroupManager;
import model.service.RecommendationManager;
import model.domain.OTTGroup;
import model.domain.OTTService;

public class CreateOTTGroupController implements Controller {

    private OTTGroupManager ottGroupManager = new OTTGroupManager();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");

        // OTT ID 파라미터 가져오기
        String ottIdParam = request.getParameter("ottId");

        // OTT ID 유효성 검사
        if (ottIdParam == null || ottIdParam.isEmpty()) {
            request.setAttribute("errorMessage", "OTT ID가 없습니다.");
            return "/errorPage.jsp";
        }

        int ottId;
        try {
            ottId = Integer.parseInt(ottIdParam);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "잘못된 OTT ID 형식입니다.");
            return "/errorPage.jsp";
        }

        // RecommendationManager를 사용해 OTTService 객체 가져오기
        RecommendationManager recManager = RecommendationManager.getInstance();
        OTTService ottService = recManager.getServiceDetailsByServiceId(String.valueOf(ottId));

        // OTTService가 유효하지 않은 경우
        if (ottService == null) {
            request.setAttribute("errorMessage", "유효하지 않은 OTT 서비스 ID입니다.");
            return "/errorPage.jsp";
        }

        // 사용자 입력값 가져오기
        String kakaoId = request.getParameter("kakaoId");
        String account = request.getParameter("account");
        String ottIdInput = request.getParameter("ottId");
        String ottPw = request.getParameter("ottPassword");

        // 입력값 유효성 검사
        if (kakaoId == null || kakaoId.isEmpty() ||
            account == null || account.isEmpty() ||
            ottIdInput == null || ottIdInput.isEmpty() ||
            ottPw == null || ottPw.isEmpty()) {
            request.setAttribute("errorMessage", "모든 필드를 입력해야 합니다.");
            request.setAttribute("ottService", ottService); // OTT 정보 유지
            return "/ottGroup/createGroup.jsp";
        }

        // 새로운 OTTGroup 생성
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId"); // 세션에서 userId 가져오기
        OTTGroup newGroup = ottGroupManager.createOTTGroup(userId, kakaoId, account, ottIdInput, ottPw, ottService);

        // 방 생성 후 OTTGroup_host.jsp로 리디렉션
        return "redirect:/ottGroup/OTTGroup_host?groupId=" + newGroup.getId();
    }
}
