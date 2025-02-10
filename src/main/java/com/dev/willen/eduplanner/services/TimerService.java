package com.dev.willen.eduplanner.services;

import com.dev.willen.eduplanner.dto.SaveTimerDto;
import com.dev.willen.eduplanner.entities.Subject;
import com.dev.willen.eduplanner.entities.Timer;
import com.dev.willen.eduplanner.entities.Topic;
import com.dev.willen.eduplanner.entities.User;
import com.dev.willen.eduplanner.repositories.TimerRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;

@Service
public class TimerService {

    private final TimerRepository repository;
    private final UserService userService;
    private final SubjectService subjectService;
    private final TopicService topicService;

    public TimerService(TimerRepository repository, UserService userService, SubjectService subjectService, TopicService topicService) {
        this.repository = repository;
        this.userService = userService;
        this.subjectService = subjectService;
        this.topicService = topicService;
    }

    public void saveTimer(SaveTimerDto timerDto, String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        Subject subject = subjectService.getSubjectById(timerDto.subjectId());
        Topic topic = topicService.getTopicById(timerDto.topicId());

        Timer timer = new Timer();
        timer.setStart(timerDto.start());
        timer.setEnd(timerDto.end());

        Duration duration = Duration.between(timerDto.start(), timerDto.end());
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        timer.setTotal(LocalTime.of((int) hours, (int) minutes));
        timer.setUser(user);
        timer.setSubject(subject);
        timer.setTopic(topic);

        repository.save(timer);
    }
}
