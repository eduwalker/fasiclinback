package com.example.enfermagemapirest.dto.response;

import java.util.List;

public record AnamneseListResponseDTO(
        UserResponseDTO userResponseDTO,

        List<AnamneseResponseDTO> anamneses

) {
}
