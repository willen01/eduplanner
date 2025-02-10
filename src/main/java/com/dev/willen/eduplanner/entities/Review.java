package com.dev.willen.eduplanner.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "tb_review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @CreationTimestamp
    private Instant createdAt;

    private Instant reviewIn;

    private int daysToReview;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Review() {
    }

    public Review(Integer id, Subject subject, Topic topic, Instant createdAt, Instant reviewIn, int daysToReview) {
        this.id = id;
        this.subject = subject;
        this.topic = topic;
        this.createdAt = createdAt;
        this.reviewIn = reviewIn;
        this.daysToReview = daysToReview;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getReviewIn() {
        return reviewIn;
    }

    public void setReviewIn(Instant reviewIn) {
        this.reviewIn = reviewIn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getDaysToReview() {
        return daysToReview;
    }

    public void setDaysToReview(int daysToReview) {
        this.daysToReview = daysToReview;
    }
}
