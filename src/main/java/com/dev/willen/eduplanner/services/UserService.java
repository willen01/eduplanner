package com.dev.willen.eduplanner.services;

import com.dev.willen.eduplanner.dto.*;
import com.dev.willen.eduplanner.entities.Authority;
import com.dev.willen.eduplanner.entities.Exercise;
import com.dev.willen.eduplanner.entities.User;
import com.dev.willen.eduplanner.enums.Role;
import com.dev.willen.eduplanner.exceptions.EmailAlreadyRegisteredException;
import com.dev.willen.eduplanner.exceptions.UserNotFoundException;
import com.dev.willen.eduplanner.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityService authorityService;
    private final ExerciseService exerciseService;
    private final AuthenticationManager authenticationManager;

    @Value("${jwt.secret.key}")
    private String JWT_SECRET;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, AuthorityService authorityService, @Lazy ExerciseService exerciseService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.authorityService = authorityService;
        this.exerciseService = exerciseService;
        this.authenticationManager = authenticationManager;
    }

    public void registerUser(CreateUserDto userDto) {
        repository.findByEmail(userDto.email()).ifPresent(err -> {
            throw new EmailAlreadyRegisteredException("User already registered!");
        });

        User userEntity = new User();
        userEntity.setFirstname(userDto.firstname());
        userEntity.setLastname(userDto.lastname());
        userEntity.setEmail(userDto.email());

        Authority authority = authorityService.getAuthorityByName(Role.USER.toString());
        userEntity.getAuthorities().add(authority);

        userEntity.setPassword(passwordEncoder.encode(userDto.password()));

        repository.save(userEntity);
    }

    public String signIn(LoginDto userRequest) {
        String jwt = "";
        Authentication authentication = UsernamePasswordAuthenticationToken
                .unauthenticated(userRequest.email(), userRequest.password());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
        // Fluxo para autenticação bem sucedida
        if (authenticationResponse != null && authenticationResponse.isAuthenticated()) {
            jwt = generateToken(authenticationResponse);
        }

        return jwt;
    }

    public void updatePassword(String userEmail, UpdatePasswordDto passwordData) {
        User user = getUserByEmail(userEmail);

        if (!passwordEncoder.matches(passwordData.actualPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        if (!passwordData.newPassword().equals(passwordData.confirmNewPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password and confirmPassword not match!");
        }

        user.setPassword(passwordEncoder.encode(passwordData.newPassword()));
        repository.save(user);
    }

    private String generateToken(Authentication authentication) {
        String secret = JWT_SECRET;
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        String jwt = Jwts.builder()
                .issuer("Eazy Bank")
                .subject("JWT token")
                .claim("username", authentication.getName())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + 30000000))
                .signWith(secretKey).compact();
        return jwt;
    }

    public User getUserByEmail(String userEmail) {
        return repository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
    }

    public InfoUserResponse infoUser(String userEmail) {
        User user = getUserByEmail(userEmail);

        int globalAnswers = user.getExercises()
                .stream()
                .mapToInt(exercise -> exercise.getCorrectAnswers() + exercise.getWrongAnswers())
                .sum();

        int globalCorrectAnswers = user.getExercises().stream()
                .mapToInt(Exercise::getCorrectAnswers)
                .sum();

        int globalWrongAnswers = user.getExercises().stream()
                .mapToInt(Exercise::getWrongAnswers)
                .sum();
        RateResponse rankingPerformance = exerciseService.highlightsRate(userEmail);

        return new InfoUserResponse(globalAnswers, globalCorrectAnswers, globalWrongAnswers, rankingPerformance);
    }
}
