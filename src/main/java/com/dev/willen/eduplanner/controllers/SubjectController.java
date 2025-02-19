package com.dev.willen.eduplanner.controllers;

import com.dev.willen.eduplanner.dto.CreateSubjectDto;
import com.dev.willen.eduplanner.dto.SubjectResponse;
import com.dev.willen.eduplanner.entities.Subject;
import com.dev.willen.eduplanner.services.SubjectService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subjects")
@Tag(name = "Subject", description = "Endpoints for managing subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/save")
    @Operation(
            summary = "Save subject",
            tags = {"Subject"},
            description = "Endpoint for save subject",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    public ResponseEntity<Void> saveSubject(@RequestBody CreateSubjectDto subject,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        subjectService.saveSubject(subject, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/remove/{id}")
    @Operation(
            summary = "Remove subject",
            tags = {"Subject"},
            description = "Endpoint for remove subject by id",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    public ResponseEntity<Void> removeSubject(@PathVariable("id") int id, @AuthenticationPrincipal UserDetails userDetails) {
        subjectService.removeSubject(id, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/all")
    @Operation(
            summary = "Get subjects",
            tags = {"Subject"},
            description = "Endpoint for get all subjects",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    public ResponseEntity<List<Subject>> getAllSubjects(@AuthenticationPrincipal UserDetails userDetails) {
        List<Subject> subjects = subjectService.getAllSubjects(userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(subjects);
    }

    @GetMapping("/findById/{id}")
    @Operation(
            summary = "Get subject",
            tags = {"Subject"},
            description = "Endpoint for get one subject by Id",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    public ResponseEntity<SubjectResponse> getSubjectById(@PathVariable("id") int id) {
        Subject subject = subjectService.getSubjectById(id);
        SubjectResponse subjectResponse = new SubjectResponse(subject.getId(), subject.getName(), subject.getCreatedAt(), subject.getTopics());

        return ResponseEntity.status(HttpStatus.OK).body(subjectResponse);
    }

}
