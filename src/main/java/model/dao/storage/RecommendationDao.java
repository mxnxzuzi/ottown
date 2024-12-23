package model.dao.storage;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.domain.OTTService;
import model.dao.JDBCUtil;

public class RecommendationDao {
    private JDBCUtil jdbcUtil = null;   // JDBCUtil 필드 선언
    
    public RecommendationDao() {
        jdbcUtil = new JDBCUtil();  
    }
 // 보관함 내 작품의 OTT 서비스별 개수를 집계하여 정렬
    public Map<Integer, Integer> getContentCountByOtt(String consumerId){
        String query = "SELECT service_id, service_name, COUNT(*) AS count ";
        query += "FROM storage JOIN OTT_CONTENT USING (content_id) JOIN OTTService (service_id) ";
        query += "WHERE consumer_id = ? GROUP BY service_id, service_name";
        
        jdbcUtil.setSqlAndParameters(query, new Object[] {consumerId});

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            Map<Integer, Integer> recommendation = new HashMap<>();
            while (rs.next()) {
                recommendation.put(rs.getInt("service_id"), rs.getInt("count"));
            }
            return recommendation;
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
    
    public Map<Integer, Integer> getOttServiceRanking(Map<Integer, Integer> count) {
        List<Map.Entry<Integer, Integer>> countList = new ArrayList<>(count.entrySet());
        
        countList.sort((entry1, entry2) -> 
            Integer.compare(entry2.getValue(), entry1.getValue())
        ); // 내림차순 정렬
        
        Map<Integer, Integer> rankMap = new LinkedHashMap<>();

        int rank = 1;
        for (Map.Entry<Integer, Integer> entry : countList) {
            rankMap.put(entry.getKey(), rank);  // service_id에 랭킹을 매핑
            rank++;
        }
        
        return rankMap;
    }
 // 보관함 내 작품의 OTT 서비스별 개수를 집계하여 정렬
    public LinkedHashMap<Integer, Integer> getRecommendationsByOTT(Long consumerId) {
        String query = "SELECT service_id, service_name, COUNT(*) AS count ";
        query += "FROM STORAGE JOIN OTT_CONTENT USING (content_id) JOIN OTTService USING (service_id) ";
        query += "WHERE consumer_id = ? GROUP BY service_id, service_name";

        jdbcUtil.setSqlAndParameters(query, new Object[] {consumerId});
        
        try {
            ResultSet rs = jdbcUtil.executeQuery();
            Map<Integer, Integer> contentCountMap = new HashMap<>();
        
            // 콘텐츠 개수 집계
            while (rs.next()) {
                contentCountMap.put(rs.getInt("service_id"), rs.getInt("count"));
            }
        
            // Map을 List로 변환하여 내림차순 정렬
            List<Map.Entry<Integer, Integer>> countList = new ArrayList<>(contentCountMap.entrySet());
            countList.sort((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue())); // 내림차순 정렬
        
            // 정렬된 데이터를 LinkedHashMap으로 반환
            LinkedHashMap<Integer, Integer> sortedContentCountMap = new LinkedHashMap<>();
            for (Map.Entry<Integer, Integer> entry : countList) {
                sortedContentCountMap.put(entry.getKey(), entry.getValue());
            }
        
            return sortedContentCountMap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }

        return null;
    }

    // service_id로 OTTService의 세부 정보를 가져오는 메소드
    public OTTService getServiceDetailsByServiceId(int serviceId) {
        String query = "SELECT image FROM OTTSERVICE WHERE service_id = ?";

        jdbcUtil.setSqlAndParameters(query, new Object[] {serviceId});
        
        try {
            ResultSet rs = jdbcUtil.executeQuery();
            for (OTTService service : OTTService.values()) {
                if (service.getId() == serviceId) {
                    if (rs.next()) {
                        service.setImage(rs.getString("image"));
                    }
                    return service;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

}
