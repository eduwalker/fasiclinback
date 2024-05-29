package com.example.enfermagemapirest.controller.user;

import lombok.Getter;

@Getter
public enum UserRole {
    MASTER(4),
    ADMIN(1),
    USER(2),
    TECSUP(3);

    private Long role;

    UserRole(int value) {
        this.role = role;
    }
}
