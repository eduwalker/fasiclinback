package com.example.enfermagemapirest.data.entity;

import jakarta.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class ProntuarioId implements Serializable {

    private String cpfPac;
    private Long codEspec;
    private Long codProf;
    private Long codProced;
    private String dataProced;

    // Getters and setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProntuarioId that = (ProntuarioId) o;
        return Objects.equals(cpfPac, that.cpfPac) &&
                Objects.equals(codEspec, that.codEspec) &&
                Objects.equals(codProf, that.codProf) &&
                Objects.equals(codProced, that.codProced) &&
                Objects.equals(dataProced, that.dataProced);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpfPac, codEspec, codProf, codProced, dataProced);
    }
}
