package com.dev.willen.eduplanner.services;

import com.dev.willen.eduplanner.entities.Authority;
import com.dev.willen.eduplanner.repositories.AuthorityRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

    private final AuthorityRepository repository;

    public AuthorityService(AuthorityRepository repository) {
        this.repository = repository;
    }

    public Authority getAuthorityByName(String authorityName) {
        return repository.findByName(authorityName).get();
    }

}
