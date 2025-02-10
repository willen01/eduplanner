package com.dev.willen.eduplanner.services;

import com.dev.willen.eduplanner.dto.ExerciseResponse;
import com.dev.willen.eduplanner.dto.SaveExerciseDto;
import com.dev.willen.eduplanner.dto.UpdateExerciseDto;
import com.dev.willen.eduplanner.entities.Exercise;
import com.dev.willen.eduplanner.entities.Subject;
import com.dev.willen.eduplanner.entities.Topic;
import com.dev.willen.eduplanner.entities.User;
import com.dev.willen.eduplanner.repositories.ExerciseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void saveExercice(SaveExerciseDto exerciseDto, String userEmail) {
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

    public List<ExerciseResponse> getAllExercices(String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        List<Exercise> all = repository.findAllByUserId(user.getId());

        List<ExerciseResponse> exerciseResponse = all.stream().map(exercise -> {
            int totalAnswers = exercise.getCorrectAnswers() + exercise.getWrongAnsweres();
            double totalPerformance = (double) exercise.getCorrectAnswers() / totalAnswers;
            double finalTotalPerformance = truncateDecimal(totalPerformance, 4) * 100;

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

    private double truncateDecimal(double value, int decimalPlaces) {
        double scale = Math.pow(10, decimalPlaces); // 10^n
        return Math.floor(value * scale) / scale; // Trunca as casas decimais extras
    }
}
