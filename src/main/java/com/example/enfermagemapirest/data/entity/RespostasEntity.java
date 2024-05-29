package com.example.enfermagemapirest.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "respostas")
public class RespostasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long answerId;

    @Column(name = "answer_text", columnDefinition = "TEXT")
    private String answerText;


    @ManyToOne
    @JoinColumn(name = "anamnese_id", referencedColumnName = "anamnese_id")
    private AnamneseEntity anamnese;


    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private PerguntasEntity pergunta;

}