package controller;

import java.util.HashMap;
import java.util.Map;

import controller.consumer.LoginController;
import controller.consumer.SignUpController;
import controller.consumer.StartPageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.storage.*;
//import controller.comm.*;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    // 각 요청 uri에 대한 controller 객체를 저장할 HashMap 생성
    private Map<String, Controller> mappings = new HashMap<String, Controller>();

    public void initMapping() {
        mappings.put("/", new ForwardController("index.jsp"));

        //consumer
        mappings.put("/consumer/signup", new SignUpController());
        mappings.put("/consumer/login", new LoginController());

        logger.info("Initialized Request Mapping!");
    }


    public Controller findController(String uri) {
        // 주어진 uri에 대응되는 controller 객체를 찾아 반환
        return mappings.get(uri);
    }
}