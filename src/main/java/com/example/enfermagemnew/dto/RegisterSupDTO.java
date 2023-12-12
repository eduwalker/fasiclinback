package com.example.enfermagemnew.dto;

import com.example.enfermagemnew.controller.user.UserRole;

public record RegisterSupDTO(
        String username,
        String nome,
        String password,
        UserRole role,
        String codProf) {
}
