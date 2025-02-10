package com.dev.willen.eduplanner.services;

import com.dev.willen.eduplanner.dto.SaveSessionDto;
import com.dev.willen.eduplanner.entities.Subject;
import com.dev.willen.eduplanner.entities.Session;
import com.dev.willen.eduplanner.entities.Topic;
import com.dev.willen.eduplanner.entities.User;
import com.dev.willen.eduplanner.repositories.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;

@Service
public class SessionService {

    private final SessionRepository repository;
    private final UserService userService;
    private final SubjectService subjectService;
    private final TopicService topicService;

    public SessionService(SessionRepository repository, UserService userService, SubjectService subjectService, TopicService topicService) {
        this.repository = repository;
        this.userService = userService;
        this.subjectService = subjectService;
        this.topicService = topicService;
    }

    public void saveSession(SaveSessionDto sessionDto, String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        Subject subject = subjectService.getSubjectById(sessionDto.subjectId());
        Topic topic = topicService.getTopicById(sessionDto.topicId());

        Session session = new Session();
        session.setStart(sessionDto.start());
        session.setEnd(sessionDto.end());

        Duration duration = Duration.between(sessionDto.start(), sessionDto.end());
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        session.setTotal(LocalTime.of((int) hours, (int) minutes));
        session.setUser(user);
        session.setSubject(subject);
        session.setTopic(topic);

        repository.save(session);
    }
}
