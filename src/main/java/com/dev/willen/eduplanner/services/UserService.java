package com.dev.willen.eduplanner.services;

import com.dev.willen.eduplanner.dto.CreateUserDto;
import com.dev.willen.eduplanner.entities.User;
import com.dev.willen.eduplanner.exceptions.DuplicatedUser;
import com.dev.willen.eduplanner.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(CreateUserDto userDto) {
        repository.findByEmail(userDto.email()).ifPresent(err -> {
            throw new DuplicatedUser("User already registered!");
        });

        User userEntity = new User();
        userEntity.setFirstname(userDto.firstname());
        userEntity.setLastname(userDto.lastname());
        userEntity.setEmail(userDto.email());
        userEntity.setPassword(passwordEncoder.encode(userDto.password()));

        repository.save(userEntity);
    }
}
