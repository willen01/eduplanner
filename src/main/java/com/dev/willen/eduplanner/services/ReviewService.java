package com.dev.willen.eduplanner.services;

import com.dev.willen.eduplanner.dto.SaveReviewDto;
import com.dev.willen.eduplanner.entities.Review;
import com.dev.willen.eduplanner.entities.Subject;
import com.dev.willen.eduplanner.entities.Topic;
import com.dev.willen.eduplanner.entities.User;
import com.dev.willen.eduplanner.repositories.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReviewService {

    private final SubjectService subjectService;
    private final TopicService topicService;
    private final ReviewRepository repository;
    private final UserService userService;

    public ReviewService(SubjectService subjectService, TopicService topicService, ReviewRepository repository,
                         UserService userService) {
        this.subjectService = subjectService;
        this.topicService = topicService;
        this.repository = repository;
        this.userService = userService;
    }

    public void saveReview(SaveReviewDto reviewDto, String userEmail) {

        Subject subject = subjectService.getSubjectById(reviewDto.SubjectId());
        Topic topic = topicService.getTopicById(reviewDto.topicId());
        User user = userService.getUserByEmail(userEmail);

        Review review = new Review();
        review.setSubject(subject);
        review.setTopic(topic);

        Instant now = Instant.now();
        review.setDaysToReview(reviewDto.daysToReview());
        review.setReviewIn(now.plus(reviewDto.daysToReview(), ChronoUnit.DAYS));
        review.setUser(user);

        repository.save(review);
    }

    public List<Review> getAllReviews(String useEmail) {
        User user = userService.getUserByEmail(useEmail);
        return repository.findByUserId(user.getId());
    }

    public void editReview(int reviewId, String userEmail, int daysToReview) {
        Review review = repository.findById(reviewId).orElseThrow(() -> {
            throw new EntityNotFoundException("Review not found");
        });

        User user = userService.getUserByEmail(userEmail);

        boolean matchWithUser = repository.existsByIdAndUserId(reviewId, user.getId());
        if (!matchWithUser) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have permission to perform this operation");
        }

        Instant actualReview = review.getReviewIn();
        review.setDaysToReview(daysToReview);
        review.setReviewIn(actualReview.plus(daysToReview, ChronoUnit.DAYS));

        repository.save(review);
    }

    public void removeReview(int reviewId, String userEmail) {
        User user = userService.getUserByEmail(userEmail);

        boolean matchWithUser = repository.existsByIdAndUserId(reviewId, user.getId());
        if (!matchWithUser) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have permission to perform this operation");
        }
        repository.deleteById(reviewId);
    }

}
