package controller.storage;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import model.domain.Content;
import model.service.StorageManager;

public class StorageFilterController implements Controller{
  //로그인 되어있는지 검사 로직 추가필요
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        
        String consumerId = request.getParameter("consumerId");
        String filter = request.getParameter("filter");
        String filterKey = request.getParameter("filterKey");
        
        StorageManager manager = StorageManager.getInstance();
        List<Content> contentList = manager.filterContent(consumerId, filter, filterKey);
        
        request.setAttribute("storage", contentList);
        return "/storage/view.jsp";
    }

}
