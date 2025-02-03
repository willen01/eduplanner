package com.dev.willen.eduplanner.services;

import com.dev.willen.eduplanner.dto.SaveTopicDto;
import com.dev.willen.eduplanner.entities.Subject;
import com.dev.willen.eduplanner.entities.Topic;
import com.dev.willen.eduplanner.entities.User;
import com.dev.willen.eduplanner.repositories.TopicRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TopicService {

    private final TopicRepository repository;
    private final SubjectService subjectService;
    private final UserService userService;

    public TopicService(TopicRepository repository, SubjectService subjectService, UserService userService) {
        this.repository = repository;
        this.subjectService = subjectService;
        this.userService = userService;
    }

    public void saveTopic(SaveTopicDto topicDto, String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        Subject subject = subjectService.getSubjectById(topicDto.subjectId());

        boolean userContainsSubject = subjectService.existsSubjectByUser(subject.getId(), user.getId());
        if (!userContainsSubject) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have permission to perform this operation");
        }

        boolean isRegistered = subject.getTopics().stream()
                .anyMatch(topic -> topic.getName().equals(topicDto.name()));

        if (isRegistered) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Topic already registered for this subject");
        }

        Topic topic = new Topic();
        topic.setName(topicDto.name());
        topic.setSubject(subject);

        repository.save(topic);
    }

}
