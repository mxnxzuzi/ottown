package controller.storage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.domain.Content;
import model.service.StorageManager;

public class AddFavController implements Controller{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        
        String consumerId = request.getParameter("consumerId");
        String contentId = request.getParameter("contentId");
        
        StorageManager manager = StorageManager.getInstance();
        int count = manager.addFav(contentId, consumerId);
        
        return null;
    }
    
}
