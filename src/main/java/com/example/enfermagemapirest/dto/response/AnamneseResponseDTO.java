package com.example.enfermagemapirest.dto.response;

import java.util.Date;

public record AnamneseResponseDTO(
        Long idAnamnese,
        Long codProf,
        String codPac,
        Date dataAnamnese,
        String statusAnamnese,
        String statusAnamneseFn,
        String observacoes,
        String nomeProf,
        int authPac,
        PacienteResponseDTO pacienteResponseDTO

) {
}
