package controller.ottGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.domain.Consumer;
import model.domain.OTTGroup;
import model.domain.OTTService;

public class ShowCreateFormController implements Controller{
       
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");

        // 요청 파라미터에서 ottId 가져오기
        String ottIdParam = request.getParameter("ottId");

        // ottId가 없거나 유효하지 않은 경우 처리
        if (ottIdParam == null || ottIdParam.isEmpty()) {
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
            request.setAttribute("errorMessage", "유효하지 않은 OTT 서비스 ID입니다.");
            return "redirect:/OTTs/view";
        }
        // JSP로 데이터 전달
        request.setAttribute("ottService", ottService);

        return "/ottGroup/createGroup.jsp";  // 데이터가 전달된 후 JSP로 이동
    }
}
