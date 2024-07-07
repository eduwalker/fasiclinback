package com.example.enfermagemapirest.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "especialidade")
@Getter
@Setter
@NoArgsConstructor
public class EspecialidadeEntity {


    @Id
    @Column(name = "cod_especialidade")
    private Long codEspecialidade;


}
