package com.dev.willen.eduplanner.services;

import com.dev.willen.eduplanner.dto.ExerciseResponse;
import com.dev.willen.eduplanner.dto.RateInfo;
import com.dev.willen.eduplanner.dto.RateResponse;
import com.dev.willen.eduplanner.dto.SaveExerciseDto;
import com.dev.willen.eduplanner.dto.TopicInfo;
import com.dev.willen.eduplanner.dto.UpdateExerciseDto;
import com.dev.willen.eduplanner.entities.Exercise;
import com.dev.willen.eduplanner.entities.Subject;
import com.dev.willen.eduplanner.entities.Topic;
import com.dev.willen.eduplanner.entities.User;
import com.dev.willen.eduplanner.repositories.ExerciseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
public class ExerciseService {

    private final ExerciseRepository repository;
    private final UserService userService;
    private final TopicService topicService;
    private final SubjectService subjectService;

    public ExerciseService(ExerciseRepository repository, UserService userService, TopicService topicService, SubjectService subjectService) {
        this.repository = repository;
        this.userService = userService;
        this.topicService = topicService;
        this.subjectService = subjectService;
    }

    public void saveExercise(SaveExerciseDto exerciseDto, String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        Subject subject = subjectService.getSubjectById(exerciseDto.subjectId());
        Topic topic = topicService.getTopicById(exerciseDto.topicId());

        Exercise exercise = new Exercise();
        exercise.setSubject(subject);
        exercise.setTopic(topic);
        exercise.setUser(user);
        exercise.setCorrectAnswers(exerciseDto.correctAnswers());
        exercise.setWrongAnsweres(exerciseDto.wrongAnswers());

        repository.save(exercise);
    }

    public List<ExerciseResponse> getAllExercises(String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        List<Exercise> exercises = repository.findAllByUserId(user.getId());

        List<ExerciseResponse> exerciseResponse = exercises.stream().map(exercise -> {
            int totalAnswers = exercise.getCorrectAnswers() + exercise.getWrongAnswers();
            double totalPerformance = (double) exercise.getCorrectAnswers() / totalAnswers;
            double finalTotalPerformance = calcRate(totalPerformance);

            return new ExerciseResponse(
                    exercise.getId(),
                    exercise.getSubject().getName(),
                    exercise.getTopic().getName(),
                    exercise.getCorrectAnswers(),
                    exercise.getWrongAnswers(),
                    finalTotalPerformance,
                    exercise.getCreatedAt());
        }).toList();

        return exerciseResponse;
    }

    public void removeExercise(int exerciseId) {
        Exercise exercise = repository.findById(exerciseId).orElseThrow(() -> {
            throw new EntityNotFoundException("Exercise not found!");
        });

        repository.delete(exercise);
    }

    public void updateExercise(UpdateExerciseDto exerciseDto, int exerciseId) {

        Exercise exercise = repository.findById(exerciseId).orElseThrow(() -> {
            throw new EntityNotFoundException("Exercise not found!");
        });

        if (exerciseDto.correctAnswers() != 0) {
            exercise.setCorrectAnswers(exerciseDto.correctAnswers());
        }

        if (exerciseDto.wrongAnswers() != 0) {
            exercise.setWrongAnswers(exerciseDto.wrongAnswers());
        }

        repository.save(exercise);
    }

    public RateResponse highlightsRate(String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        List<TopicInfo> topics = repository.getTopicInfo(user.getId());

        if (topics.isEmpty()) {
            return new RateResponse(null, null);
        }

        if(topics.size() > 1) {
            TopicInfo maxRateTopic = topics.stream()
                    .max(Comparator.comparingDouble(this::calcTopicRate))
                    .get();

            TopicInfo minRateTopic = topics.stream()
                    .min(Comparator.comparingDouble(this::calcTopicRate))
                    .get();

            RateInfo bestRateInfo = createRateInfo(maxRateTopic);
            RateInfo worstRateInfo = createRateInfo(minRateTopic);

            return new RateResponse(bestRateInfo, worstRateInfo);
        } else {
            return new RateResponse(null, null);
        }
    }

    public List<RateInfo> getRanking(String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        List<TopicInfo> topics = repository.getTopicInfo(user.getId());

        if (topics.isEmpty()) {
            return Collections.emptyList();
        }

        List<TopicInfo> topicsSorted = topics.stream()
                .sorted(Comparator.comparingDouble(this::calcTopicRate).reversed())
                .toList();

        return topicsSorted.stream()
                .map(this::createRateInfo)
                .toList();
    }

    private RateInfo createRateInfo(TopicInfo topicInfo) {
        Topic topic = topicService.getTopicById(topicInfo.topicId());
        Subject subject = subjectService.getSubjectById(topic.getSubject().getId());
        double performance = calcTopicRate(topicInfo);
        return new RateInfo(subject.getName(), topic.getName(), performance);
    }

    private double calcRate(double value) {
        double result = value * 100;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("#.##", symbols);
        return Double.parseDouble(df.format(result));
    }

    private double calcTopicRate(TopicInfo bestPerformance) {
        double decimalValue = (double) bestPerformance.correctAnswers() / bestPerformance.total();
        return calcRate(decimalValue);
    }
}
