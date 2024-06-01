package com.example.enfermagemapirest.dto.request;

import java.util.List;

public record AnamneseAnswerRequest(
        Long anamneseID,
        List<PerguntaResposta> respostas
) {}
