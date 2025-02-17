package com.dev.willen.eduplanner.controllers;

import com.dev.willen.eduplanner.dto.SaveSessionDto;
import com.dev.willen.eduplanner.dto.SessionInfo;
import com.dev.willen.eduplanner.services.SessionService;
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
@RequestMapping("/api/v1/sessions")
public class SessionController {

    private final SessionService service;

    public SessionController(SessionService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveSession(@RequestBody SaveSessionDto sessionDto, @AuthenticationPrincipal UserDetails userDetails) {
        service.saveSession(sessionDto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<SessionInfo>> getAllSessions(@AuthenticationPrincipal UserDetails userDetails) {
        List<SessionInfo> response = service.getAllSessions(userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeSession(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") int id) {
        service.removeSession(userDetails.getUsername(), id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
