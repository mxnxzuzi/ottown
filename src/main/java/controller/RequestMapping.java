package controller;

import java.util.HashMap;
import java.util.Map;

import controller.consumer.LoginController;
import controller.consumer.SignUpController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.homepage.ViewContentController;
import controller.homepage.ViewContentReviewController;
import controller.mainPage.MainpageController;
import controller.ottservice.OttTopListController;
import controller.storage.*;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    
    // 각 요청 uri에 대한 controller 객체를 저장할 HashMap 생성
    private Map<String, Controller> mappings = new HashMap<String, Controller>();

    public void initMapping() {
    	// 각 uri에 대응되는 controller 객체를 생성 및 저장
    	mappings.put("/", new ForwardController("index.jsp"));
    	mappings.put("/mainpage", new MainpageController());

        //consumer
        mappings.put("/consumer/signup", new SignUpController());
        mappings.put("/consumer/login", new LoginController());

        // 보관함 기능
        mappings.put("/storage/recommend", new StorageOTTRecommendationController());
        mappings.put("/storage/view/filter", new StorageFilterController());
        mappings.put("/storage/add", new AddFavController());
        mappings.put("/storage/delete", new DeleteFavController());
        mappings.put("/storage/view", new ViewStorageController());
        
    	//content
    	mappings.put("/content/top10", new OttTopListController());
    	mappings.put("/content/view", new ViewContentController());
    	
    	//리뷰
    	mappings.put("/review/view", new ViewContentReviewController());
    	
        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String uri) {	
    	// 주어진 uri에 대응되는 controller 객체를 찾아 반환
        return mappings.get(uri);
    }
}