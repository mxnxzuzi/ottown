package model.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import model.dto.Review;

public interface ReviewMapper {
    // 리뷰 추가
    int addReview(Review review);

    // 특정 콘텐츠의 리뷰 조회
    List<Review> getReviewsByContentId(@Param("contentId") long contentId);
    
    // 특정 유저의 리뷰 조회
    List<Review> getReviewsByConsumerId(@Param("consumerId") long contentId);

    // 리뷰 수정
    int updateReview(Review review);

    // 리뷰 삭제
    int deleteReview(@Param("reviewId") long reviewId);
}
