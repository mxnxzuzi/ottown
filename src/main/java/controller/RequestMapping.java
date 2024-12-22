package controller;

import java.util.HashMap;
import java.util.Map;

import controller.consumer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.homepage.ViewContentController;
import controller.mainPage.MainpageController;
import controller.notification.NotificationListController;
import controller.ottGroup.CreateOTTGroupController;
import controller.ottGroup.PassToOTTGroupController;
import controller.ottGroup.ShowCreateFormController;
import controller.ottGroup.ShowHostController;
import controller.ottGroup.ShowMyGroupListController;
import controller.ottGroup.ShowOTTGroupListController;
import controller.ottGroup.ShowOTTsController;
import controller.ottGroupMember.JoinOTTGroupController;
import controller.ottGroupMember.KickMemberController;
import controller.ottGroupMember.SetPaidController;
import controller.ottGroupMember.ShareOTTInfoController;
import controller.ottGroupMember.ShowMemberController;
import controller.ottservice.OttTopListController;
import controller.review.AddReviewController;
import controller.review.DeleteReviewController;
import controller.review.UpdateReviewController;
import controller.review.ViewConsumerReviewController;
import controller.review.ViewContentReviewController;
import controller.review.ViewMyReviewController;
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
        mappings.put("/consumer/logout", new LogoutController());
        mappings.put("/consumer/mypage", new MypageController());
        mappings.put("/consumer/delete", new DeleteUserController());
        mappings.put("/consumer/update", new UpdateUserController());


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
    	mappings.put("/content/review/view", new ViewContentReviewController());
    	mappings.put("/review/add", new AddReviewController());
    	mappings.put("/consumer/review/view", new ViewConsumerReviewController());
    	mappings.put("/mypage/review/view", new ViewMyReviewController());
    	mappings.put("/review/delete", new DeleteReviewController());
    	mappings.put("/review/update", new UpdateReviewController());
    	
    	//알림 
    	mappings.put("/notification", new NotificationListController());

    	 // OTT 관련 요청 처리
        mappings.put("/OTTs/view", new ShowOTTsController()); // OTT 리스트 조회
        
        // 공동구매방 관련 요청 처리
        mappings.put("/ottGroupList/OTTGroupList", new ShowOTTGroupListController()); // 공동구매방 리스트 조회
        mappings.put("/ottGroup/show/createForm", new ShowCreateFormController()); // 공동구매방 생성 페이지
        mappings.put("/ottGroup/createGroup", new CreateOTTGroupController()); // 공동구매방 생성 페이지
        mappings.put("/ottGroup/joinGroup", new JoinOTTGroupController()); // 공동구매방 참여
        mappings.put("/ottGroup/OTTGroup_member", new ShowMemberController()); // 멤버의 공동구매방 화면 조회
        
        // 공동구매방 상세 화면
        mappings.put("/mypage/ottGroup/view", new ShowMyGroupListController());   // 공동구매방 연결
        mappings.put("/ottGroup/OTTGroup_host", new ShowHostController()); // 호스트의 공동구매방 화면 조회
        mappings.put("/ottGroup/share", new ShareOTTInfoController()); // OTT 계정 정보 공유
        mappings.put("/ottGroup/removeMember", new KickMemberController()); // 멤버 강제퇴장
        mappings.put("/ottGroup/member/isPaid", new SetPaidController()); // 멤버 입금 확인
        mappings.put("/mypage/ottGroup/view/group", new PassToOTTGroupController());
    	
        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String uri) {	
    	// 주어진 uri에 대응되는 controller 객체를 찾아 반환
        return mappings.get(uri);
    }
}