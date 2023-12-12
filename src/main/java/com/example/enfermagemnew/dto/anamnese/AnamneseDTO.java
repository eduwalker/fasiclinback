package com.example.enfermagemnew.dto.anamnese;

import java.util.Date;

public record AnamneseDTO(
        Long idUser,
        Long idSupervisor,
        String cpfPaciente,
        Date dataPreenchimento
) {
}
