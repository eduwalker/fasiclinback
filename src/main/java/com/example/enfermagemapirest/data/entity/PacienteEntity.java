package com.example.enfermagemapirest.data.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "paciente")
public class PacienteEntity {

    @Id
    @Column(name = "cpf_pac", nullable = false, length = 11)
    private String cpfPac;

    @Column(name = "nome_pac", nullable = false, length = 100)
    private String nomePac;

    @Column(name = "cod_pac", nullable = false)
    private Long codPac;

    @Column(name = "tel_pac", nullable = false, length = 11)
    private String telPac;

    @Column(name = "cep_pac", nullable = false, length = 8)
    private String cepPac;

    @Column(name = "logra_pac", nullable = false, length = 100)
    private String lograPac;

    @Column(name = "num_logra_pac", nullable = false)
    private Long numLograPac;

    @Column(name = "compl_pac", length = 20)
    private String complPac;

    @Column(name = "bairro_pac", nullable = false, length = 50)
    private String bairroPac;

    @Column(name = "cidade_pac", nullable = false, length = 50)
    private String cidadePac;

    @Column(name = "uf_pac", nullable = false, length = 2)
    private String ufPac;

    @Column(name = "rg_pac", length = 12)
    private String rgPac;

    @Column(name = "est_rg_pac", length = 2)
    private String estRgPac;

    @Column(name = "nome_mae_pac", length = 100)
    private String nomeMaePac;

    @Column(name = "data_nasc_pac", nullable = false)
    private LocalDate dataNascPac;
}