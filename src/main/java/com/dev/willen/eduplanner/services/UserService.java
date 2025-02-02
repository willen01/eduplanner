package com.dev.willen.eduplanner.services;

import com.dev.willen.eduplanner.dto.CreateUserDto;
import com.dev.willen.eduplanner.entities.User;
import com.dev.willen.eduplanner.exceptions.DuplicatedUser;
import com.dev.willen.eduplanner.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void registerUser(CreateUserDto userDto) {
        repository.findByEmail(userDto.email()).ifPresent(err -> {
            throw new DuplicatedUser("User already registered!");
        });

        User userEntity = new User();
        userEntity.setFirstname(userDto.firstname());
        userEntity.setLastname(userDto.lastname());
        userEntity.setEmail(userDto.email());
        userEntity.setPassword(userDto.password());

        repository.save(userEntity);
    }
}
