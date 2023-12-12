package com.example.enfermagemnew.entity.anamnese;
import jakarta.persistence.*;
import lombok.Data;

    @Entity
    @Table(name = "perguntasanamnese")
    @Data
    public class PerguntasAnamneseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_pergunta")
        private Long idPergunta;


        @Column(name = "texto_pergunta", columnDefinition = "TEXT", nullable = false)
        private String textoPergunta;

        @Enumerated(EnumType.STRING)
        @Column(name = "tipo_resposta", nullable = false)
        private TipoResposta tipoResposta;



        public enum TipoResposta {
            texto, UNICA_ESCOLHA, MULTIPLA_ESCOLHA
        }
    }