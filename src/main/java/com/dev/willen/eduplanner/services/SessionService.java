package com.dev.willen.eduplanner.services;

import com.dev.willen.eduplanner.dto.SaveSessionDto;
import com.dev.willen.eduplanner.entities.Session;
import com.dev.willen.eduplanner.entities.Subject;
import com.dev.willen.eduplanner.entities.Topic;
import com.dev.willen.eduplanner.repositories.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class SessionService {

    private final SubjectService subjectService;
    private final TopicService topicService;
    private final SessionRepository repository;

    public SessionService(SubjectService subjectService, TopicService topicService, SessionRepository repository) {
        this.subjectService = subjectService;
        this.topicService = topicService;
        this.repository = repository;
    }

    public void saveSession(SaveSessionDto sessionDto) {

        Subject subject = subjectService.getSubjectById(sessionDto.SubjectId());
        Topic topic = topicService.getTopicById(sessionDto.topicId());

        Session session = new Session();
        session.setSubject(subject);
        session.setTopic(topic);

        Instant now = Instant.now();
        session.setReviewIn(now.plus(sessionDto.reviewIn(), ChronoUnit.DAYS));

        repository.save(session);
    }

}
