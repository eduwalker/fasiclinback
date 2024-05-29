package com.example.enfermagemapirest.dto.request;

import java.util.List;

public record AnamneseRespostasDTO(
        Integer anamneseID,
        List<PerguntaResposta> respostas
) {

}