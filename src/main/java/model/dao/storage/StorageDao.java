package model.dao.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.domain.Content;
import model.dao.JDBCUtil;

public class StorageDao {
    private JDBCUtil jdbcUtil = null;   // JDBCUtil 필드 선언
    
    public StorageDao() {
        jdbcUtil = new JDBCUtil();  
    }
    
    public int addFav(Long contentId, Long consumerId) {
        String query = "INSERT INTO storage (storage_id, content_id, consumer_id) VALUES (SEQ_STORAGE.NEXTVAL, ?, ?)";
        jdbcUtil.setSqlAndParameters(query, new Object[] {contentId, consumerId});

        try {
            int result = jdbcUtil.executeUpdate();
            jdbcUtil.commit();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        
        return 0;
    }

    // 보관함에서 작품 삭제
    public int deleteFav(Long contentId, Long consumerId) {
        String query = "DELETE FROM storage WHERE content_id = ? AND consumer_id = ?";

        jdbcUtil.setSqlAndParameters(query, new Object[] {contentId, consumerId});

        try {
            int result =  jdbcUtil.executeUpdate();
            jdbcUtil.commit();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        
        return 0;
    }

    // OTT별로 보관함 조회
    public List<Content> getContentsByOTTService(Long consumerId, String ottServiceName) {
        String query = "SELECT content_id, title, type, genre, c.image, publishdate ";
        query += "FROM CONTENT c JOIN STORAGE USING (content_id) JOIN OTT_CONTENT USING (content_id) JOIN OTTService USING (service_id)";
        query += "WHERE consumer_id = ? AND service_name = ?";
        
        jdbcUtil.setSqlAndParameters(query, new Object[] {consumerId, ottServiceName});
        try {
            ResultSet rs = jdbcUtil.executeQuery();
            List<Content> contentList = new ArrayList<>();

            while (rs.next()) {
                Content content = new Content(
                    rs.getLong("content_id"),
                    rs.getString("title"),
                    rs.getString("type"),
                    rs.getString("genre"),
                    rs.getString("image"),
                    rs.getDate("publishdate")
                );
                contentList.add(content);
            }
            return contentList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
    
    public List<Content> showStorage(Long consumerId) {
        String sql = "SELECT content_id, title, image FROM CONTENT JOIN STORAGE USING (content_id) WHERE consumer_id = ?";
        
        jdbcUtil.setSqlAndParameters(sql, new Object[]{consumerId});
        try {
            ResultSet rs = jdbcUtil.executeQuery();
            List<Content> contentList = new ArrayList<>();
            
            while (rs.next()) {
                Content content = new Content();
                content.setContentId(rs.getLong("content_id"));
                content.setTitle(rs.getString("title"));
                content.setImage(rs.getString("image"));
                contentList.add(content);
            }
            return contentList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }
    
    public int getTotalContentCount(Long consumerId) {
        String sql = "SELECT COUNT(*) AS totalCount FROM storage WHERE consumer_id = ?";
        
        jdbcUtil.setSqlAndParameters(sql, new Object[]{consumerId});
        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                return rs.getInt("totalCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return 0;
    }

}
