package com.dev.willen.eduplanner.dto;

public record UpdatePasswordDto(String actualPassword, String newPassword, String confirmNewPassword) {
}
