package com.example.enfermagemnew.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "profissional")
@Data
public class ProfissionalEntity {

    @Id
    @Column(name = "cod_prof", nullable = false)
    private Long codProf;

    @Column(name = "nome_prof", nullable = false)
    private String nomeProf;
//    @Column(name = "password", nullable = false)
//    private String password;

    @Column(name = "tipo_prof", nullable = false)
    private Long tipoProf;

    @Column(name = "sup_prof")
    private Long supProf;

    @Column(name = "status_prof", nullable = false)
    private Integer statusProf;

    @Column(name = "cons_prof")
    private String consProf;



}
