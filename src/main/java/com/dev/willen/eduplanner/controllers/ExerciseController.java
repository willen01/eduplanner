package com.dev.willen.eduplanner.controllers;

import com.dev.willen.eduplanner.dto.ExerciseResponse;
import com.dev.willen.eduplanner.dto.RateInfo;
import com.dev.willen.eduplanner.dto.SaveExerciseDto;
import com.dev.willen.eduplanner.dto.UpdateExerciseDto;
import com.dev.willen.eduplanner.services.ExerciseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Exercise", description = "Endpoints for managing exercises")
public class ExerciseController {

    private final ExerciseService service;

    public ExerciseController(ExerciseService service) {
        this.service = service;
    }

    @PostMapping("/save")
    @Operation(
            summary = "Save exercise",
            tags = {"Exercise"},
            description = "Endpoint for save a new exercise",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    public ResponseEntity<Void> saveExercise(@RequestBody SaveExerciseDto request,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        service.saveExercise(request, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    @Operation(
            summary = "Get all exercises",
            tags = {"Exercise"},
            description = "Endpoint to get all exercises",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    public ResponseEntity<List<ExerciseResponse>> getAllExercises(@AuthenticationPrincipal UserDetails userDetails) {
        List<ExerciseResponse> allExercices = service.getAllExercises(userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(allExercices);
    }

    @DeleteMapping("/remove/{id}")
    @Operation(
            summary = "Remove exercise",
            tags = {"Exercise"},
            description = "Endpoint for remove exercise by id",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    public ResponseEntity<Void> removeExercise(@PathVariable(name = "id") int id) {
        service.removeExercise(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("update/{id}")
    @Operation(
            summary = "Update exercise",
            tags = {"Exercise"},
            description = "Endpoint for update exercise",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    public ResponseEntity<Void> updateExercise(@PathVariable(name = "id") int id, @RequestBody UpdateExerciseDto updateExercise) {
        service.updateExercise(updateExercise, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/rates")
    @Operation(
            summary = "Get Rates",
            tags = {"Exercise"},
            description = "Endpoint for classifying exercises by performance",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    public ResponseEntity<List<RateInfo>> performanceRate(@AuthenticationPrincipal UserDetails userDetails) {
        List<RateInfo>  rating = service.getRanking(userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(rating);
    }
}
