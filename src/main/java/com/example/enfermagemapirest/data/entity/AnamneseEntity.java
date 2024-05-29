package com.example.enfermagemapirest.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "anamneses")
@Getter
@Setter
@NoArgsConstructor
public class AnamneseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anamnese_id")
    private Long anamneseId;

    @Column(name = "cpf_pac", nullable = false)
    private String cpfPac;

    @Column(name = "cod_prof", nullable = false)
    private Long codProf;

    @Column(name = "data_anamnese", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAnamnese;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_anamnese", nullable = false)
    private StatusAnamnese statusAnamnese;

    @Column(name = "status_anamnese_fn")
    private String statusAnamneseFn;

    @ManyToOne
    @JoinColumn(name = "cpf_pac", referencedColumnName = "cpf_pac", insertable = false, updatable = false)
    private PacienteEntity paciente;

    @ManyToOne
    @JoinColumn(name = "cod_prof", referencedColumnName = "cod_prof", insertable = false, updatable = false)
    private ProfissionalEntity profissional;



}