package controller.storage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
//import controller.user.UserSessionUtils;
import controller.consumer.UserSessionUtils;
import model.domain.OTTService;
import model.service.RecommendationManager;
import model.service.StorageManager;

public class StorageOTTRecommendationController implements Controller{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        HttpSession session = request.getSession();
        
        if (!UserSessionUtils.hasLogined(session)) { return "redirect:/consumer/login"; }
        
        request.setCharacterEncoding("utf-8");
        
        String consumerId = UserSessionUtils.getLoginUserId(session);
        // RecommendationService 호출
        RecommendationManager recManager = RecommendationManager.getInstance();
        StorageManager storageManger = StorageManager.getInstance();
        
        LinkedHashMap<Integer, Integer> recommendationList = recManager.getRecommendationsByOTT(consumerId);
        
        if (recommendationList == null) {
            recommendationList = new LinkedHashMap<>();
        }
        
        List<Map<String, Object>> detailedRecommendations = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : recommendationList.entrySet()) {
            int serviceId = entry.getKey();
            int count = entry.getValue();

            // OTT 서비스 정보 가져오기
            OTTService ottService = recManager.getServiceDetailsByServiceId(String.valueOf(serviceId));

            if (ottService != null) {
                // Map에 OTT 정보와 작품 개수 추가
                Map<String, Object> recommendation = new LinkedHashMap<>();
                recommendation.put("id", ottService.getId());
                recommendation.put("name", ottService.getName());
                recommendation.put("image", ottService.getImage());
                recommendation.put("count", count);
                detailedRecommendations.add(recommendation);
            }
        }
        
        int total = storageManger.getTotalContentCount(consumerId);

        // 추천 결과 JSP로 전달
        request.setAttribute("totalCount", total);
        request.setAttribute("recommendations", detailedRecommendations);
        return "/storage/recommend.jsp";
    }
    
}
