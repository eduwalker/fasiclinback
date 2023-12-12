package com.example.enfermagemnew.dto;

import com.example.enfermagemnew.controller.user.UserRole;

public record RegisterDTO(
        String username,
        String nome,
        String password,
        UserRole role,
        String codProf,
        String supProf
                          ) {
}
