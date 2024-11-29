package controller.homepage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.dto.Content;
import model.service.ContentManager;

public class ViewContentController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String type = request.getParameter("type");
		if (type == null || type.isEmpty()) {
			type = "movie";
		}

		ContentManager contentManager = new ContentManager();
		List<Content> contents = contentManager.showContentList(type);

		request.setAttribute("contents", contents);

		switch (type) {
		case "movie":
		case "drama":
		case "animation":
			return "/homePage/contentView.jsp?type=" + type; 
		default:
			return "/homePage/contentView.jsp?type=movie"; 
		}

	}

}
