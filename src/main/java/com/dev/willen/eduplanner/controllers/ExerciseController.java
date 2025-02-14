package com.dev.willen.eduplanner.controllers;

import com.dev.willen.eduplanner.dto.*;
import com.dev.willen.eduplanner.services.ExerciseService;
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
@RequestMapping("/api/v1/exercises")
public class ExerciseController {

    private final ExerciseService service;

    public ExerciseController(ExerciseService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveExercise(@RequestBody SaveExerciseDto request,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        service.saveExercise(request, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExerciseResponse>> getAllExercises(@AuthenticationPrincipal UserDetails userDetails) {
        List<ExerciseResponse> allExercices = service.getAllExercises(userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(allExercices);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeExercise(@PathVariable(name = "id") int id) {
        service.removeExercise(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Void> updateExercise(@PathVariable(name = "id") int id, @RequestBody UpdateExerciseDto updateExercise) {
        service.updateExercise(updateExercise, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/rates")
    public ResponseEntity<List<RateInfo>> performanceRate(@AuthenticationPrincipal UserDetails userDetails) {
        List<RateInfo>  rating = service.getRanking(userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(rating);
    }
}
