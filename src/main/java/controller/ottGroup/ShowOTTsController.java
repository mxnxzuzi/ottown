package controller.ottGroup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.dao.OTTGroup.OTTGroupDao;
import model.domain.OTTService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShowOTTsController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        OTTGroupDao ottGroupDao = new OTTGroupDao();

        // OTTService Enum에서 모든 서비스를 가져와 리스트 구성
        List<Map<String, Object>> ottServiceList = new ArrayList<>();
        for (OTTService service : OTTService.values()) {
            Map<String, Object> ottData = new LinkedHashMap<>();
            ottData.put("id", service.getId());
            ottData.put("name", service.getName());

            // DAO를 통해 OTTService의 이미지 데이터 가져오기
            OTTService detailedService = ottGroupDao.getServiceDetailsByServiceId(service.getId());
            if (detailedService != null) {
                ottData.put("image", detailedService.getImage());
            } else {
                ottData.put("image", "/images/default.jpg"); // 기본 이미지 설정
            }

            ottServiceList.add(ottData);
        }

        // JSP로 전달
        request.setAttribute("ottServices", ottServiceList);

        // 결과 페이지 반환
        return "/OTTs/view.jsp";
    }
}