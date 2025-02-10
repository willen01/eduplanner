package com.dev.willen.eduplanner.entities;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "tb_session")
public class Session {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "startTime")
    private LocalTime start;

    @Column(name = "endTime")
    private LocalTime end;

    private LocalTime total;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    public Session() {
    }

    public Session(Integer id, LocalTime start, LocalTime end, LocalTime total, User user, Subject subject, Topic topic) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.total = total;
        this.user = user;
        this.subject = subject;
        this.topic = topic;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public LocalTime getTotal() {
        return total;
    }

    public void setTotal(LocalTime total) {
        this.total = total;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
