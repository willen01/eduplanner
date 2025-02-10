package com.dev.willen.eduplanner.controllers;

import com.dev.willen.eduplanner.dto.SaveTimerDto;
import com.dev.willen.eduplanner.services.TimerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/timers")
public class TimerController {

    private final TimerService service;

    public TimerController(TimerService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveTimer(@RequestBody SaveTimerDto timerDto, @AuthenticationPrincipal UserDetails userDetails) {
        service.saveTimer(timerDto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
