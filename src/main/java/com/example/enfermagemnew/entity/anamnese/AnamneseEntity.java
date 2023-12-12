package com.example.enfermagemnew.entity.anamnese;
import com.example.enfermagemnew.entity.PacienteEntity;
import com.example.enfermagemnew.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Entity
@Table(name = "anamneses")
@Data
public class AnamneseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_anamnese")
    private Long idAnamnese;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private UserEntity idUser;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpf_pac", nullable = false)
    private PacienteEntity paciente;

    @Column(name = "data_preenchimento", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPreenchimento;

    @Column(name = "id_supervisor")
    private Long supervisor;
}
