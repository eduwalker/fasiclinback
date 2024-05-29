package com.example.enfermagemapirest.dto.response;

public record UserResponseDTO(
        String nome,
        Long codProf,
        Long supervisor,
        Long tipo,
        int status,
        String consProf

) {
}
