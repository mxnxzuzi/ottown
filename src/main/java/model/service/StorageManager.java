package model.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;

import model.dao.storage.StorageDao;
import model.domain.Content;

public class StorageManager {
    private static StorageManager storageMan = new StorageManager();
    private StorageDao storageDao;
    
    private StorageManager() {
        try {
            storageDao = new StorageDao();
        } catch (Exception e) {
            e.printStackTrace();
        }           
    }
    
    public static StorageManager getInstance() {
        return storageMan;
    }
    //Exception 추가하기
    public int addFav(String contentId, String consumerId) throws SQLException{
        
        return storageDao.addFav(Long.parseLong(contentId), Long.parseLong(consumerId));
    }
    public int deleteFav(String contentId, String consumerId) throws SQLException{
        return storageDao.deleteFav(Long.parseLong(contentId), Long.parseLong(consumerId));
    }
    
    public List<Content> filterContent(String consumerId, String filter, String filterKey) throws SQLException{
        if (filter.equals("genre")) {
            return storageDao.getContentsByGenre(Long.parseLong(consumerId), filterKey);
        }
        else if (filter.equals("ott")) {
            return storageDao.getContentsByOTTService(Long.parseLong(consumerId), filterKey);
        }
        return null;
    }
    /*public List<Content> getContentsByGenre(Long consumerId, String genre) throws SQLException{
        return storageDao.getContentsByGenre(consumerId, genre);
    }
    public List<Content> getContentsByOTTService(Long consumerId, String ottServiceName)throws SQLException{
        return storageDao.getContentsByOTTService(consumerId, ottServiceName);
    }*/
    public List<Content> showStorage(String consumerId) throws SQLException{
        return storageDao.showStorage(Long.parseLong(consumerId));
    }
    
    public int getTotalContentCount(String counsumerId) throws SQLException{
        return storageDao.getTotalContentCount(Long.parseLong(counsumerId));
    }
}
