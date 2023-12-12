package com.example.enfermagemnew.dto.anamnese;

import com.example.enfermagemnew.entity.anamnese.PerguntasAnamneseEntity;

public record PerguntasResponseDTO(Long idPergunta, String textoPergunta) {

    public PerguntasResponseDTO(PerguntasAnamneseEntity pergunta){
        this(pergunta.getIdPergunta(), pergunta.getTextoPergunta());
    }
}
