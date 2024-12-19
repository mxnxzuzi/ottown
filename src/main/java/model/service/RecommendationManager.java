package model.service;

import java.util.LinkedHashMap;

import model.dao.storage.RecommendationDao;
import model.domain.OTTService;

public class RecommendationManager {
    private static RecommendationManager recMan = new RecommendationManager();
    private RecommendationDao recDao;
    
    private RecommendationManager() {
        try {
            recDao = new RecommendationDao();
        } catch (Exception e) {
            e.printStackTrace();
        }           
    }
    
    public static RecommendationManager getInstance() {
        return recMan;
    }
    
    public LinkedHashMap<Integer, Integer> getRecommendationsByOTT(String consumerId) {
        return recDao.getRecommendationsByOTT(Long.parseLong(consumerId));
    }
    
    public OTTService getServiceDetailsByServiceId(String serviceId) {
        return recDao.getServiceDetailsByServiceId(Integer.parseInt(serviceId));
    }
}
