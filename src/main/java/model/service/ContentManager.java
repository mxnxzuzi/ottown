package model.service;

import java.sql.SQLException;
import java.util.List;

import model.dao.content.ContentDao;
import model.dto.Content;

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
    
	public void deleteAllContents() {
		try {
			contentDao.deleteAllContents();
			System.out.println("Content 테이블의 모든 데이터를 삭제했습니다.");
		} catch (SQLException e) {
			System.err.println("데이터 삭제 중 오류 발생: " + e.getMessage());
		}
	}
}