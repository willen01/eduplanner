package com.dev.willen.eduplanner.controllers;

import com.dev.willen.eduplanner.dto.ExerciseResponse;
import com.dev.willen.eduplanner.dto.SaveExerciseDto;
import com.dev.willen.eduplanner.dto.UpdateExerciseDto;
import com.dev.willen.eduplanner.services.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
        service.saveExercice(request, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExerciseResponse>> getAllExercises(@AuthenticationPrincipal UserDetails userDetails) {
        List<ExerciseResponse> allExercices = service.getAllExercices(userDetails.getUsername());
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
}
