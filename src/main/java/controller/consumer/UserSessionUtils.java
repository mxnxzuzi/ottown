package controller.consumer;

import javax.servlet.http.HttpSession;

public class UserSessionUtils {
    // 세션에 저장된 사용자 ID를 구분하기 위한 키값
    public static final String USER_SESSION_KEY = "userSessionKey";
    public static final String CONSUMER_NAME_KEY = "consumerName";
    public static final String CONSUMER_EMAIL_KEY = "consumerEmail";
    public static final String CONSUMER_PASSWORD_KEY = "consumerPassword";

    /* 현재 로그인한 사용자의 ID를 구함 */
    public static String getLoginUserId(HttpSession session) {
        if (session == null) {
//            System.out.println("session is null");
            return null;
        }
        Object userId = session.getAttribute(USER_SESSION_KEY);
        if (userId == null) {
//            System.out.println("user id is null");
            return null;  // 로그인하지 않은 상태
        }
        return userId.toString();  // 로그인한 사용자 ID를 문자열로 반환
    }

    /* 로그인한 상태인지를 검사 */
    public static boolean hasLogined(HttpSession session) {
        return getLoginUserId(session) != null;  // 세션에 저장된 userId가 null이 아니면 로그인 상태
    }

    /* 현재 로그인한 사용자의 ID가 userId인지 검사 */
    public static boolean isLoginUser(String userId, HttpSession session) {
        String loginUser = getLoginUserId(session);
        return loginUser != null && loginUser.equals(userId);  // 로그인한 사용자 ID가 주어진 userId와 같으면 true
    }

    /* 로그인한 사용자의 이름을 구함 */
    public static String getConsumerName(HttpSession session) {
        if (session == null) {
            return null;
        }
        return (String) session.getAttribute(CONSUMER_NAME_KEY);  // 세션에서 consumerName 가져오기
    }

    /* 로그인한 사용자의 이메일을 구함 */
    public static String getConsumerEmail(HttpSession session) {
        if (session == null) {
            return null;
        }
        return (String) session.getAttribute(CONSUMER_EMAIL_KEY);  // 세션에서 consumerEmail 가져오기
    }

    /* 로그인한 사용자 정보를 세션에 저장 */
    public static void setConsumerInfo(HttpSession session, String consumerName, String consumerEmail, String consumerPassword) {
        session.setAttribute(CONSUMER_NAME_KEY, consumerName);  // 세션에 이름 저장
        session.setAttribute(CONSUMER_EMAIL_KEY, consumerEmail);  // 세션에 이메일 저장
        session.setAttribute(CONSUMER_PASSWORD_KEY, consumerPassword);
    }
}
