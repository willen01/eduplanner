package com.dev.willen.eduplanner.controllers;

import com.dev.willen.eduplanner.dto.CreateUserDto;
import com.dev.willen.eduplanner.dto.InfoUserResponse;
import com.dev.willen.eduplanner.dto.LoginDto;
import com.dev.willen.eduplanner.dto.UpdatePasswordDto;
import com.dev.willen.eduplanner.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User Management", description = "Endpoints to basic operations for user/client")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            tags = {"User Management"},
            description = "Endpoint for registering new users"
    )
    public ResponseEntity<Void> registeUser(@RequestBody CreateUserDto userRequest) {
        userService.registerUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signIn")
    @Operation(
            summary = "Login user",
            tags = {"User Management"},
            description = "Endpoint for registered user login")
    public ResponseEntity<Void> signIn(@RequestBody LoginDto userRequest) {
        String response = userService.signIn(userRequest);
        return ResponseEntity.status(HttpStatus.OK).header("token_response", response)
                .build();
    }

    @PostMapping("/updatePass")
    @Operation(
            summary = "Update password",
            tags = {"User Management"},
            description = "Endpoint for password update",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    public ResponseEntity<Void> updatePass(@AuthenticationPrincipal UserDetails userDetails,
                                           @RequestBody UpdatePasswordDto request) {
        userService.updatePassword(userDetails.getUsername(), request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/info")
    @Operation(
            summary = "Info user",
            tags = {"User Management"},
            description = "Endpoint for general user information, such as performance statistics and platform usage",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    public ResponseEntity<InfoUserResponse> infoResponse(@AuthenticationPrincipal UserDetails userDetails) {
        InfoUserResponse response = userService.infoUser(userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
