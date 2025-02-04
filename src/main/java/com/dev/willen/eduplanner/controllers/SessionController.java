package com.dev.willen.eduplanner.controllers;

import com.dev.willen.eduplanner.dto.SaveSessionDto;
import com.dev.willen.eduplanner.dto.SaveSession;
import com.dev.willen.eduplanner.entities.Session;
import com.dev.willen.eduplanner.services.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    private final SessionService service;

    public SessionController(SessionService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> registerSessionn(@RequestBody SaveSessionDto sessionRequest,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        service.saveSession(sessionRequest, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Session>> findAllSessions(@AuthenticationPrincipal UserDetails userDetails) {
        List<Session> response = service.getAllSessions(userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/edit")
    public ResponseEntity<Void> editSession(@RequestBody SaveSession request,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        service.editSession(request.sessionId(), userDetails.getUsername(), request.daysToReview());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
