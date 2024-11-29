package model.dao.content;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.domain.Content;
import model.dao.JDBCUtil;

public class ContentDao {
    private JDBCUtil jdbcUtil = null;
    private Map<String, String> koreanToEnglishMap;
    
    public ContentDao() {
        jdbcUtil = new JDBCUtil(); // JDBCUtil 객체 생성
        koreanToEnglishMap = new HashMap<>();
        koreanToEnglishMap.put("넷플릭스", "netflix");
        koreanToEnglishMap.put("티빙", "tving");
        koreanToEnglishMap.put("쿠팡플레이", "coupangplay");
        koreanToEnglishMap.put("디즈니+", "disneyplus");
        koreanToEnglishMap.put("웨이브", "wavve");
        koreanToEnglishMap.put("왓챠", "watcha");
    }

    // 데이터 삽입 (Create)
    public int insertContent(Content content) throws SQLException {
        String sql = "INSERT INTO Content (content_id, title, type, genre, image, publishDate) "
                + "VALUES (seq_content.NEXTVAL, ?, ?, ?, ?, ?)";
        Object[] param = new Object[] { content.getTitle(), content.getType(), content.getGenre(), content.getImage(),
                content.getPublishDate() != null ? new java.sql.Date(content.getPublishDate().getTime()) : null };

        jdbcUtil.setSqlAndParameters(sql, param);

        try {
            int result = jdbcUtil.executeUpdate();
            return result; // 삽입 성공 시 1 반환
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }
        return 0;
    }

    public void insertContents(List<Content> contents) throws Exception {
        String sql = "INSERT INTO Content (content_id, title, type, genre, image, publishDate) "
                + "VALUES (seq_content.NEXTVAL, ?, ?, ?, ?, ?)";

        try {
            for (Content content : contents) {
                Object[] param = new Object[] {
                    content.getTitle(),
                    content.getType(),
                    content.getGenre(),
                    content.getImage(),
                    content.getPublishDate() != null ? new java.sql.Date(content.getPublishDate().getTime()) : null
                };

                // 디버깅 로그 추가
                System.out.println("실행할 SQL: " + sql);
                System.out.println("매개변수: " + Arrays.toString(param));

                jdbcUtil.setSqlAndParameters(sql, param);
                jdbcUtil.executeUpdate();
            }

            System.out.println("모든 Content 데이터가 성공적으로 삽입되었습니다.");
        } catch (SQLException e) {
            System.err.println("Content 데이터를 삽입하는 중 오류 발생: " + e.getMessage());
            jdbcUtil.rollback(); // 예외 발생 시 롤백
            throw e;
        } finally {
            jdbcUtil.commit(); // 작업 완료 후 커밋
            jdbcUtil.close(); // 리소스 해제
        }
    }

    // 데이터 조회 (Read)
    public List<Content> findAllByType(String type) throws SQLException {
        String sql = "SELECT content_id, title, type, genre, image, publishDate FROM Content WHERE type = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] { type });

        List<Content> contentList = new ArrayList<>();

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs == null) {
                System.err.println("ResultSet is null. Query execution failed.");
                return contentList; // 빈 리스트 반환
            }

            while (rs.next()) {
                Content content = new Content();
                content.setContentId(rs.getLong("content_id"));
                content.setTitle(rs.getString("title"));
                content.setType(rs.getString("type"));
                content.setGenre(rs.getString("genre"));
                content.setImage(rs.getString("image"));
                content.setPublishDate(rs.getDate("publishDate"));

                contentList.add(content);
                System.err.println(content);
            }
        } catch (SQLException e) {
            System.err.println("콘텐츠 조회 중 오류 발생: " + e.getMessage());
            throw e;
        } finally {
            jdbcUtil.close();
        }

        return contentList;
    }


    // 데이터 수정 (Update)
    public int updateContent(Content content) throws SQLException {
        String sql = "UPDATE Content SET title = ?, type = ?, genre = ?, image = ?, publishDate = ? WHERE content_id = ?";
        Object[] param = new Object[] { content.getTitle(), content.getType(), content.getGenre(), content.getImage(),
                content.getPublishDate() != null ? new java.sql.Date(content.getPublishDate().getTime()) : null,
                content.getContentId() };

        jdbcUtil.setSqlAndParameters(sql, param);

        try {
            int result = jdbcUtil.executeUpdate();
            return result; // 수정 성공 시 1 반환
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }
        return 0;
    }

    // 데이터 삭제 (Delete)
    public int deleteContent(int contentId) throws SQLException {
        String sql = "DELETE FROM Content WHERE content_id = ?";
        Object[] param = new Object[] { contentId };

        jdbcUtil.setSqlAndParameters(sql, param);

        try {
            int result = jdbcUtil.executeUpdate();
            return result; // 삭제 성공 시 1 반환
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }
        return 0;
    }
    //데이터 전체 삭제
    public void deleteAllContents() throws SQLException {
        String sql = "DELETE FROM Content";

        jdbcUtil.setSqlAndParameters(sql, null); // 파라미터가 없으므로 null 설정

        try {
            int result = jdbcUtil.executeUpdate();
            System.out.println(result + "개의 데이터가 삭제되었습니다.");
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }
    }
    public void deleteAllOTTContents() throws SQLException {
        String sql = "DELETE FROM OTT_Content";

        jdbcUtil.setSqlAndParameters(sql, null); // 파라미터가 없으므로 null 설정

        try {
            int result = jdbcUtil.executeUpdate();
            System.out.println(result + "개의 데이터가 삭제되었습니다.");
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close();
        }
    }
    // OTT 서비스를 저장하기 위한 메서드 추가
    public void saveOttContent(int contentId, List<String> ottServices) throws Exception {
        String sql = "INSERT INTO ott_content (content_id, service_id) VALUES (?, ?)";

        try {
            for (String ottService : ottServices) {
                // service_name을 기반으로 service_id를 조회
                String englishServiceName = koreanToEnglishMap.getOrDefault(ottService, ottService);
                int serviceId = getServiceIdByName(englishServiceName);
                if (serviceId != -1) { // 유효한 service_id인 경우에만 삽입
                    Object[] param = new Object[]{contentId, serviceId};
                    jdbcUtil.setSqlAndParameters(sql, param);
                    jdbcUtil.executeUpdate();
                    jdbcUtil.commit(); // 각 삽입 후 커밋
                    System.out.println("OTT Content 저장 성공: content_id=" + contentId + ", service_id=" + serviceId);
                } else {
                    System.out.println("OTT 서비스 매칭 실패: " + ottService);
                }
            }
        } catch (SQLException e) {
            System.err.println("OTT Content 저장 중 오류 발생: " + e.getMessage());
            jdbcUtil.rollback(); // 오류 발생 시 롤백
        } finally {
            jdbcUtil.close(); // 리소스 해제
        }
    }

    private int getServiceIdByName(String serviceName) {
        String sql = "SELECT SERVICE_ID FROM OTTSERVICE WHERE LOWER(SERVICE_NAME) = LOWER(?)";
        Object[] param = new Object[] { serviceName.trim() };
        jdbcUtil.setSqlAndParameters(sql, param);
        
        try {
            System.out.println("매칭 시도 중 (쿼리 실행): " + serviceName); // 크롤링된 값 출력
            ResultSet rs = jdbcUtil.executeQuery();
            
            if (rs != null && rs.next()) {
                int serviceId = rs.getInt("service_id");
                System.out.println("매칭 성공: " + serviceName + " (서비스 ID: " + serviceId + ")");
                return serviceId;
            }
        } catch (SQLException e) {
            System.err.println("service_name 조회 중 오류 발생: " + e.getMessage());
        } finally {
            jdbcUtil.close();
        }
        
        System.out.println("매칭 실패 (쿼리 실패): " + serviceName); // 매칭 실패한 값 출력
        return -1;
    }


    public int getContentIdByName(Content content) throws SQLException {
        String sql = "SELECT content_id FROM Content WHERE title = ?";
        Object[] param = new Object[]{content.getTitle()};

        jdbcUtil.setSqlAndParameters(sql, param);

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                return rs.getInt("content_id");
            }
        } catch (SQLException e) {
            System.err.println("Content ID 조회 중 오류 발생: " + e.getMessage());
        } finally {
            jdbcUtil.close(); // 리소스 해제
        }
        return -1; // content_id를 찾지 못한 경우
    }

    public List<String> findOTTByContentId(Long contentId) throws SQLException {
        String sql = "SELECT o.SERVICE_NAME " +
                     "FROM OTT_CONTENT oc " +
                     "JOIN OTTSERVICE o ON oc.SERVICE_ID = o.SERVICE_ID " +
                     "WHERE oc.CONTENT_ID = ?";
        Object[] param = new Object[] { contentId };

        jdbcUtil.setSqlAndParameters(sql, param);

        List<String> ottServices = new ArrayList<>();
        try {
            ResultSet rs = jdbcUtil.executeQuery();
            while (rs.next()) {
                ottServices.add(rs.getString("SERVICE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return ottServices;
    }
    
    public boolean isLiked(Long contentId, Long consumerId) {
        String sql = "SELECT COUNT(*) AS exist FROM storage WHERE content_id = ? AND consumer_id = ?";
        
        jdbcUtil.setSqlAndParameters(sql, new Object[]{contentId, consumerId});
        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                if (rs.getInt("exist") > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return false;
    }

}