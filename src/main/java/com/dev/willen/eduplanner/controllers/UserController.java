package com.dev.willen.eduplanner.controllers;

import com.dev.willen.eduplanner.dto.CreateUserDto;
import com.dev.willen.eduplanner.dto.InfoUserResponse;
import com.dev.willen.eduplanner.dto.LoginDto;
import com.dev.willen.eduplanner.dto.UpdatePasswordDto;
import com.dev.willen.eduplanner.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registeUser(@RequestBody CreateUserDto userRequest) {
        userService.registerUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signIn")
    public ResponseEntity<Void> signIn(@RequestBody LoginDto userRequest) {
        String response = userService.signIn(userRequest);
        return ResponseEntity.status(HttpStatus.OK).header("toke_response", response)
                .build();
    }

    @PostMapping("/updatePass")
    public ResponseEntity<Void> updatePass(@AuthenticationPrincipal UserDetails userDetails,
                                           @RequestBody UpdatePasswordDto request) {
        userService.updatePassword(userDetails.getUsername(), request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/info")
    public ResponseEntity<InfoUserResponse> infoResponse(@AuthenticationPrincipal UserDetails userDetails) {
        InfoUserResponse response = userService.infoUser(userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
