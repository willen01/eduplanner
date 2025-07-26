package com.dev.willen.eduplanner.repositories;

import com.dev.willen.eduplanner.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByUserId(Integer userId);

    boolean existsByIdAndUserId(Integer reviewId, Integer userId);
}
