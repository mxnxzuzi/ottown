package model.service;

import java.sql.SQLException;
import java.util.List;

import model.dao.content.ContentDao;
import model.domain.Content;

public class ContentManager {
    private ContentDao contentDao = new ContentDao();

    public void saveContents(List<Content> contents) {
        try {
            contentDao.insertContents(contents);
            System.out.println("영화 데이터가 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            System.out.println("영화 데이터를 저장하는 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void insertContent(Content content) {
        try {
        	System.out.println("매니저" + content);
            contentDao.insertContent(content);
            System.out.println("영화 데이터가 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            System.out.println("영화 데이터를 저장하는 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
	public void deleteAllContents() {
		try {
			contentDao.deleteAllContents();
			System.out.println("Content 테이블의 모든 데이터를 삭제했습니다.");
		} catch (SQLException e) {
			System.err.println("데이터 삭제 중 오류 발생: " + e.getMessage());
		}
	}
	public void deleteAllOTTContents() {
		try {
			contentDao.deleteAllOTTContents();
			System.out.println("OTTContent 테이블의 모든 데이터를 삭제했습니다.");
		} catch (SQLException e) {
			System.err.println("데이터 삭제 중 오류 발생: " + e.getMessage());
		}
	}

	public int getContentIdByName(Content content) {
	    try {
	        return contentDao.getContentIdByName(content); // DAO의 메서드를 호출
	    } catch (SQLException e) {
	        System.err.println("ContentManager - Content ID 조회 중 오류 발생: " + e.getMessage());
	    }
	    return -1; // 오류 발생 시 -1 반환
	}

	public void saveOttContent(int contentId, List<String> ottServices) {
	    try {
	        // ContentDao의 saveOttContent 호출
	        contentDao.saveOttContent(contentId, ottServices);
	        System.out.println("OTT Content 저장 완료: content_id=" + contentId);
	    } catch (Exception e) {
	        System.err.println("ContentManager - OTT Content 저장 중 오류 발생: " + e.getMessage());
	    }
	}


	public List<Content> showContentList(String type, String consumerId) throws SQLException {
        List<Content> contents = contentDao.findAllByType(type);

        for (Content content : contents) {
            // 콘텐츠 ID로 OTT 정보를 가져와서 설정
            List<String> ottServices = contentDao.findOTTByContentId(content.getContentId());
            content.setOttServices(ottServices);
            
            //isLiked 설정
            if (consumerId != null) {
                if (contentDao.isLiked(content.getContentId(), Long.parseLong(consumerId))) {
                    content.setIsLiked(true);
                }
            }
        }
        return contents;
    }
	
	public Content getContentById(String contentId) throws SQLException {
	    try {
            return contentDao.getContentById(Long.parseLong(contentId));
        } catch (Exception e) {
            System.err.println("ContentManager - Content ID 조회 중 오류 발생: " + e.getMessage());
        }
	    return null;
	}

}