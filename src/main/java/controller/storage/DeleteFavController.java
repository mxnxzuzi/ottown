package controller.storage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.service.StorageManager;

public class DeleteFavController implements Controller{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        
        String consumerId = request.getParameter("consumerId");
        String contentId = request.getParameter("contentId");
        
        StorageManager manager = StorageManager.getInstance();
        int count = manager.deleteFav(contentId, consumerId);
        
        return null;
    }
    
}
