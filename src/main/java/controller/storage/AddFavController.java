package controller.storage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
//import controller.user.UserSessionUtils;
import controller.consumer.UserSessionUtils;
import model.domain.Content;
import model.service.StorageManager;
//import model.service.UserManager;

public class AddFavController implements Controller{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        HttpSession session = request.getSession();
        
        if (!UserSessionUtils.hasLogined(session)) { return "redirect:/consumer/login"; }
        
        request.setCharacterEncoding("utf-8");
        
        String consumerId = UserSessionUtils.getLoginUserId(session);
        String contentId = request.getParameter("contentId");
        String type = request.getParameter("type");
        if (type == null) {
            return "redirect:/content/view"; 
        }
        
        StorageManager manager = StorageManager.getInstance();
        manager.addFav(contentId, consumerId);

        switch (type) {
            case "movie":
                return "redirect:/content/view?type=movie";
            case "drama":
                return "redirect:/content/view?type=drama";
            case "animation":
                return "redirect:/content/view?type=animation";
            default:
                return "redirect:/content/view";
        }
    }
}
