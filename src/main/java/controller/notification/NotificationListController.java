package controller.notification;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import controller.Controller;
import model.domain.Notification;
import util.MyBatisUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class NotificationListController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	Long userId = (Long) request.getSession().getAttribute("userSessionKey"); // Long으로 수정
        if (userId == null) {
            response.sendRedirect("/login");
            return null;
        }

        SqlSessionFactory sqlSessionFactory = MyBatisUtils.getSqlSessionFactory();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            NotificationMapper mapper = session.getMapper(NotificationMapper.class);
            List<Notification> notifications = mapper.getNotificationsByUserId(userId);

            request.setAttribute("notifications", notifications);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("알림 목록을 가져오는 중 오류 발생", e);
        }

        return "/notification/notificationList.jsp";
    }
}
