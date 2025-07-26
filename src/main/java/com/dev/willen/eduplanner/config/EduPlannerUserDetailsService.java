package com.dev.willen.eduplanner.config;

import com.dev.willen.eduplanner.entities.User;
import com.dev.willen.eduplanner.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EduPlannerUserDetailsService implements UserDetailsService {

    private final UserRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(EduPlannerUserDetailsService.class);

    public EduPlannerUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username)
                .orElseThrow(() -> {
                            logger.error("User with email: {} not found", username);
                            throw new BadCredentialsException("User or password incorrect, try again!");
                        }
                );

        List<GrantedAuthority> authorities = user.getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
