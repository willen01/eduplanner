package com.dev.willen.eduplanner.services;

import com.dev.willen.eduplanner.dto.SaveSessionDto;
import com.dev.willen.eduplanner.entities.Session;
import com.dev.willen.eduplanner.entities.Subject;
import com.dev.willen.eduplanner.entities.Topic;
import com.dev.willen.eduplanner.entities.User;
import com.dev.willen.eduplanner.repositories.SessionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class SessionService {

    private final SubjectService subjectService;
    private final TopicService topicService;
    private final SessionRepository repository;
    private final UserService userService;

    public SessionService(SubjectService subjectService, TopicService topicService, SessionRepository repository, UserService userService) {
        this.subjectService = subjectService;
        this.topicService = topicService;
        this.repository = repository;
        this.userService = userService;
    }

    public void saveSession(SaveSessionDto sessionDto, String userEmail) {

        Subject subject = subjectService.getSubjectById(sessionDto.SubjectId());
        Topic topic = topicService.getTopicById(sessionDto.topicId());
        User user = userService.getUserByEmail(userEmail);

        Session session = new Session();
        session.setSubject(subject);
        session.setTopic(topic);

        Instant now = Instant.now();
        session.setDaysToReview(sessionDto.daysToReview());
        session.setReviewIn(now.plus(sessionDto.daysToReview(), ChronoUnit.DAYS));
        session.setUser(user);

        repository.save(session);
    }

    public List<Session> getAllSessions(String useEmail) {
        User user = userService.getUserByEmail(useEmail);
        return repository.findByUserId(user.getId());
    }

    public void editSession(int sessionId, String userEmail, int daysToReview) {
        Session session = repository.findById(sessionId).orElseThrow(() -> {
            throw new EntityNotFoundException("Session not found");
        });

        User user = userService.getUserByEmail(userEmail);

        boolean matchWithUser = repository.existsByIdAndUserId(sessionId, user.getId());
        if (!matchWithUser) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have permission to perform this operation");
        }

        Instant actualReview = session.getReviewIn();
        session.setDaysToReview(daysToReview);
        session.setReviewIn(actualReview.plus(daysToReview, ChronoUnit.DAYS));

        repository.save(session);
    }

    public void removeSession(int sessionId, String userEmail) {
        User user = userService.getUserByEmail(userEmail);

        boolean matchWithUser = repository.existsByIdAndUserId(sessionId, user.getId());
        if (!matchWithUser) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have permission to perform this operation");
        }
        repository.deleteById(sessionId);
    }

}
