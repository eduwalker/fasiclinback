package com.example.enfermagemnew.entity.anamnese;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "respostasanamnese")
@Data
public class RespostasAnamneseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resposta")
    private Long idResposta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pergunta", nullable = false)
    private PerguntasAnamneseEntity pergunta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_anamnese", nullable = false)
    private AnamneseEntity anamnese;

    @Column(name = "resposta", columnDefinition = "TEXT")
    private String resposta;

}