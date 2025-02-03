package com.dev.willen.eduplanner.controllers;

import com.dev.willen.eduplanner.dto.CreateSubjectDto;
import com.dev.willen.eduplanner.services.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveSubject(@RequestBody CreateSubjectDto subject,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        subjectService.saveSubject(subject, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeSubject(@PathVariable("id") int id, @AuthenticationPrincipal UserDetails userDetails) {
        subjectService.removeSubject(id, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
