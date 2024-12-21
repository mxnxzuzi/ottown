package controller.storage;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
//import controller.user.UserSessionUtils;
import controller.consumer.UserSessionUtils;
import model.domain.Content;
import model.service.StorageManager;

public class StorageFilterController implements Controller{
  //로그인 되어있는지 검사 로직 추가필요
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        HttpSession session = request.getSession();
        
        if (!UserSessionUtils.hasLogined(session)) { return "redirect:/consumer/login"; }
        
        request.setCharacterEncoding("utf-8");
        
        String consumerId = UserSessionUtils.getLoginUserId(session);

        String filterKey = request.getParameter("filterkey");
        
        if (filterKey != null) {
            StorageManager manager = StorageManager.getInstance();
            List<Content> contentList = manager.getContentsByOTTService(consumerId, filterKey);
            
            request.setAttribute("storage", contentList);
            return "/storage/view.jsp";
        }
        return "redirect:/storage/view";
    }

}
