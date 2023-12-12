package com.example.enfermagemnew.entity.anamnese;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "anamnesepdf")
public class AnamnesePdfEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pdf")
    private Long idPdf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_anamnese", nullable = false)
    private AnamneseEntity anamnese;

    @Column(name = "caminho_pdf", nullable = false)
    private String caminhoPdf;

    @Column(name = "data_geracao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataGeracao;

    // Getters e setters
}