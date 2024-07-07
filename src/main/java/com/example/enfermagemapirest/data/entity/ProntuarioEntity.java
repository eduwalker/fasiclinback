package com.example.enfermagemapirest.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "prontuario")
@IdClass(ProntuarioId.class)
public class ProntuarioEntity {

    @Id
    @Column(name = "cpf_pac", nullable = false, length = 11)
    private String cpfPac;

    @Id
    @Column(name = "cod_espec", nullable = false)
    private Long codEspec;

    @Id
    @Column(name = "cod_prof", nullable = false)
    private Long codProf;

    @Id
    @Column(name = "cod_proced", nullable = false)
    private Long codProced;

    @Id
    @Column(name = "data_proced", nullable = false)
    private String dataProced;

    @Column(name = "descr_proced", columnDefinition = "TEXT")
    private String descrProced;

    @Column(name = "link_proced", length = 40)
    private String linkProced;

    @Column(name = "aut_vis_pac", columnDefinition = "TINYINT(1) DEFAULT 0")
    private int autVisPac;

}
