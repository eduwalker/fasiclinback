package com.example.enfermagemapirest.dto.response;

public record AnamneseAnswerResponse(
        boolean success,
        String message,
        Long anamneseId
) {
}
