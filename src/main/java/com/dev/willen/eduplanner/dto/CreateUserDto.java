package com.dev.willen.eduplanner.dto;

public record CreateUserDto(
        String firstname,
        String lastname,
        String email,
        String password
) {
}
