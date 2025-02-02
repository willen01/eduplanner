package com.dev.willen.eduplanner.services;

import com.dev.willen.eduplanner.dto.CreateUserDto;
import com.dev.willen.eduplanner.entities.Authority;
import com.dev.willen.eduplanner.entities.User;
import com.dev.willen.eduplanner.enums.Role;
import com.dev.willen.eduplanner.exceptions.DuplicatedUser;
import com.dev.willen.eduplanner.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityService authorityService;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, AuthorityService authorityService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.authorityService = authorityService;
    }

    public void registerUser(CreateUserDto userDto) {
        repository.findByEmail(userDto.email()).ifPresent(err -> {
            throw new DuplicatedUser("User already registered!");
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
}
