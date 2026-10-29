package com.example.verifyendpoint.user;

public record UserResponse(Long id, String name, String email) {

    public static UserResponse from(UserEntity user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
