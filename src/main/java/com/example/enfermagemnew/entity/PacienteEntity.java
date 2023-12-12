package com.example.enfermagemnew.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "paciente")
@Data
public class PacienteEntity {

    @Id
    @Column(name = "cpf_pac", nullable = false)
    private String cpfPac;

    @Column(name = "nome_pac", nullable = false)
    private String nomePac;

    @Column(name = "cod_pac", nullable = false)
    private Long codPac;

    @Column(name = "tel_pac", nullable = false)
    private String telPac;

    @Column(name = "cep_pac", nullable = false)
    private String cepPac;

    @Column(name = "logra_pac", nullable = false)
    private String lograPac;

    @Column(name = "num_logra_pac", nullable = false)
    private Long numLograPac;

    @Column(name = "compl_pac")
    private String complPac;

    @Column(name = "bairro_pac", nullable = false)
    private String bairroPac;

    @Column(name = "cidade_pac", nullable = false)
    private String cidadePac;

    @Column(name = "uf_pac", nullable = false)
    private String ufPac;

    @Column(name = "rg_pac")
    private String rgPac;

    @Column(name = "est_rg_pac")
    private String estRgPac;

    @Column(name = "nome_mae_pac")
    private String nomeMaePac;

    @Column(name = "data_nasc_pac", nullable = false)
    private Date dataNascPac;

    // Getters e setters
}
