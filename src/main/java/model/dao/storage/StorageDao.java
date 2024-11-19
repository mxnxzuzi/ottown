package model.dao.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.domain.Storage;
import model.domain.Content;
import model.dao.JDBCUtil;

public class StorageDao {
    private JDBCUtil jdbcUtil = null;   // JDBCUtil 필드 선언
    
    public StorageDao() {
        jdbcUtil = new JDBCUtil();  
    }
    
    public void addFav(Long contentId, String consumerId) {
        String query = "INSERT INTO storage (content_id, consumer_id) VALUES (?, ?)";
        jdbcUtil.setSqlAndParameters(query, new Object[] {contentId, consumerId});

        try {
            jdbcUtil.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
    }

    // 보관함에서 작품 삭제
    public void deleteFav(Long contentId, String consumerId) {
        String query = "DELETE FROM storage WHERE content_id = ? AND consumer_id = ?";

        jdbcUtil.setSqlAndParameters(query, new Object[] {contentId, consumerId});

        try {
            jdbcUtil.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
    }

    // 장르별로 보관함 조회
    public List<Content> getContentsByGenre(String consumerId, String genre) {
        String query = "SELECT content_id, title, type, genre, image, publish_date FROM content JOIN storage USING (content_id) WHERE consumer_id = ? AND genre = ?";

        jdbcUtil.setSqlAndParameters(query, new Object[] {consumerId, genre});
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
                    rs.getDate("publishDate")
                );
                contentList.add(content);
            }
            return contentList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

    // OTT별로 보관함 조회
    public List<Content> getContentsByOTTService(String consumerId, String ottServiceName) {
        String query = "SELECT content_id, title, type, genre, image, publish_date ";
        query += "FROM content JOIN storage USING (content_id) JOIN OTTcontent USING (content_id) JOIN OTTService USING (service_id)";
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
                    rs.getDate("publish_date")
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
        String sql = "SELECT content_id, title, image FROM content JOIN storage USING (content_id) WHERE consumer_id = ?";
        
        jdbcUtil.setSqlAndParameters(sql, new Object[]{consumerId});
        try {
            ResultSet rs = jdbcUtil.executeQuery();
            List<Content> contentList = new ArrayList<>();
            
            while (rs.next()) {
                Content content = new Content();
                content.setContentId(rs.getLong("content_id"));
                content.setTitle(rs.getString("title"));
                content.setType(rs.getString("image"));
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

}
