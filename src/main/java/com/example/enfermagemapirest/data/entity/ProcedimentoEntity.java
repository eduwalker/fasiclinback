package com.example.enfermagemapirest.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "procedimento")
@Data
public class ProcedimentoEntity {

    @Id
    private Long codProced;
    private String descrProced;
    private Double valProced;
}