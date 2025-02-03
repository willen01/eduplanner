package com.dev.willen.eduplanner.controllers;

import com.dev.willen.eduplanner.services.SessionService;
import com.dev.willen.eduplanner.dto.SaveSessionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
