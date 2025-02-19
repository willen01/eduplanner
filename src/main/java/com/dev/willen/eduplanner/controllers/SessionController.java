package com.dev.willen.eduplanner.controllers;

import com.dev.willen.eduplanner.dto.SaveSessionDto;
import com.dev.willen.eduplanner.dto.SessionInfo;
import com.dev.willen.eduplanner.services.SessionService;
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
@RequestMapping("/api/v1/sessions")
@Tag(name = "Session", description = "Endpoints for managing sessions")
public class SessionController {

    private final SessionService service;

    public SessionController(SessionService service) {
        this.service = service;
    }

    @PostMapping("/save")
    @Operation(
            summary = "Save session",
            tags = {"Session"},
            description = "Endpoint for save a new session",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    public ResponseEntity<Void> saveSession(@RequestBody SaveSessionDto sessionDto, @AuthenticationPrincipal UserDetails userDetails) {
        service.saveSession(sessionDto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    @Operation(
            summary = "Get sessions",
            tags = {"Session"},
            description = "Endpoint for get session by id",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    public ResponseEntity<List<SessionInfo>> getAllSessions(@AuthenticationPrincipal UserDetails userDetails) {
        List<SessionInfo> response = service.getAllSessions(userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/remove/{id}")
    @Operation(
            summary = "Remove session",
            tags = {"Session"},
            description = "Endpoint for remove session",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    public ResponseEntity<Void> removeSession(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") int id) {
        service.removeSession(userDetails.getUsername(), id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
