package controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.OTTGroup.*;
import controller.OTTGroupMember.*;
import controller.mainPage.MainpageController;
import controller.storage.*;
import controller.homepage.*;
import controller.ottservice.*;

public class RequestMapping {

    private static final Logger logger = LoggerFactory.getLogger(RequestMapping.class);
    
    // 각 요청 URI에 대한 Controller 객체를 저장할 HashMap
    private Map<String, Controller> mappings = new HashMap<String, Controller>();

    // Request URI에 대응되는 Controller 매핑 초기화
    public void initMapping() {
    	// 각 uri에 대응되는 controller 객체를 생성 및 저장
    	mappings.put("/", new ForwardController("index.jsp"));
    	mappings.put("/mainpage", new MainpageController());
        
        // 보관함 기능
        mappings.put("/storage/recommend", new StorageOTTRecommendationController());
        mappings.put("/storage/view/filter", new StorageFilterController());
        mappings.put("/storage/add", new AddFavController());
        mappings.put("/storage/delete", new DeleteFavController());
        mappings.put("/storage/view", new ViewStorageController());
        
    	//content
    	mappings.put("/content/top10", new OttTopListController());
    	mappings.put("/content/view", new ViewContentController());
    	
        // 기본 페이지 매핑
        mappings.put("/", new ForwardController("index.jsp"));
        
        // OTT 관련 요청 처리
        mappings.put("/OTTs/view", new ShowOTTsController()); // OTT 리스트 조회
        
        // 공동구매방 관련 요청 처리
        mappings.put("/ottGroupList/OTTGroupList", new ShowOTTGroupListController()); // 공동구매방 리스트 조회
        mappings.put("/ottGroup/createGroup", new CreateOTTGroupController()); // 공동구매방 생성 페이지
        mappings.put("/ottGroup/OTTGroup_member", new JoinOTTGroupController()); // 공동구매방 참여
        
        // 공동구매방 상세 화면
        mappings.put("/ottGroup/view", new ShowOTTGroupController());	// 공동구매방 연결
        mappings.put("/ottGroup/OTTGroup_host", new ShowHostController()); // 호스트의 공동구매방 화면 조회
        mappings.put("/ottGroup/share", new ShareOTTInfoController()); // OTT 계정 정보 공유
        mappings.put("/ottGroup/host/kick", new KickMemberController()); // 멤버 강제퇴장
        mappings.put("/ottGroup/member/isPaid", new SetPaidController()); // 멤버 입금 확인
        
        // 로깅: 초기화가 완료되었음을 알림
        logger.info("Initialized Request Mapping!");
    }

    // 요청 URI에 해당하는 Controller를 반환
    public Controller findController(String uri) {
        // 주어진 URI에 대응되는 Controller 객체 반환
        return mappings.get(uri);
    }
}