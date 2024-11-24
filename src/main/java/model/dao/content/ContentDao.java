package model.dao.content;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.dto.Content;
import model.dao.JDBCUtil;

public class ContentDao {
	private JDBCUtil jdbcUtil = null;

	public ContentDao() {
		jdbcUtil = new JDBCUtil(); // JDBCUtil 객체 생성
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
	public List<Content> getAllContents() throws SQLException {
		String sql = "SELECT * FROM Content";
		jdbcUtil.setSqlAndParameters(sql, null);

		List<Content> contentList = new ArrayList<>();

		try {
			ResultSet rs = jdbcUtil.executeQuery();
			while (rs.next()) {
				Content content = new Content();
				content.setContentId(rs.getInt("content_id"));
				content.setTitle(rs.getString("title"));
				content.setType(rs.getString("type"));
				content.setGenre(rs.getString("genre"));
				content.setImage(rs.getString("image"));
				content.setPublishDate(rs.getDate("publishDate"));
				contentList.add(content);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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

}
