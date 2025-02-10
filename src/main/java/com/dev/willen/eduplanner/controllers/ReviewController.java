package com.dev.willen.eduplanner.controllers;

import com.dev.willen.eduplanner.dto.EditReviewDto;
import com.dev.willen.eduplanner.dto.SaveReviewDto;
import com.dev.willen.eduplanner.entities.Review;
import com.dev.willen.eduplanner.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> registerReview(@RequestBody SaveReviewDto reviewRequestRequest,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        service.saveReview(reviewRequestRequest, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Review>> findAllReviews(@AuthenticationPrincipal UserDetails userDetails) {
        List<Review> response = service.getAllReviews(userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/edit")
    public ResponseEntity<Void> editReview(@RequestBody EditReviewDto review,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        service.editReview(review.reviewId(), userDetails.getUsername(), review.daysToReview());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeReview(@PathVariable("id") int reviewId,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        service.removeReview(reviewId, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
