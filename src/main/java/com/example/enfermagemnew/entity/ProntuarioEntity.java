package com.example.enfermagemnew.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "prontuario")
@Data
public class ProntuarioEntity {

    @Id
    @Column(name = "cpf_pac", nullable = false)
    private String cpfPac;

    @Column(name = "cod_espec", nullable = false)
    private Long codEspec;

    @Column(name = "cod_prof", nullable = false)
    private Long codProf;

    @Column(name = "cod_proced", nullable = false)
    private Long codProced;

    @Column(name = "data_proced", nullable = false)
    private Date dataProced;

    @Column(name = "descr_proced", nullable = false, columnDefinition = "TEXT")
    private String descrProced;

    @Column(name = "link_proced")
    private String linkProced;

    @Column(name = "aut_vis_pac", nullable = false)
    private Boolean autVisPac;

}
