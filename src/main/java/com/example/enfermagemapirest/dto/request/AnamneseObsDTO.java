package com.example.enfermagemapirest.dto.request;

public record AnamneseObsDTO(
        Long anamneseId,
        String status,
        String observacoes
) {
}
