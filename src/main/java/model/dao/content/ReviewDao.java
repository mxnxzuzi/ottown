package model.dao.content;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import model.dto.Review;
import model.dao.JDBCUtil;
import model.dao.mapper.ReviewMapper;

public class ReviewDao {
    private SqlSessionFactory sqlSessionFactory;
    
    public ReviewDao() {
        String resource = "mybatis-config.xml";
        InputStream inputStream;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }
    
    public int addReview(Review review) {  
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int result = sqlSession.getMapper(ReviewMapper.class).addReview(review);
            if (result > 0) {
                sqlSession.commit();
            } 
            return result;
        } finally {
            sqlSession.close();
        }
    }

    public List<Review> getReviewsByContentId(Long contentId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            return sqlSession.getMapper(ReviewMapper.class).getReviewsByContentId(contentId);          
        } finally {
            sqlSession.close();
        }
    }

    public int updateReview(Review review) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int result = sqlSession.getMapper(ReviewMapper.class).updateReview(review);
            if (result > 0) {
                sqlSession.commit();
            } 
            return result;
        } finally {
            sqlSession.close();
        }
    }

    public int deleteReview(Long reviewId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int result = sqlSession.getMapper(ReviewMapper.class).deleteReview(reviewId);
            if (result > 0) {
                sqlSession.commit();
            } 
            return result;
        } finally {
            sqlSession.close();
        }
    }
}
