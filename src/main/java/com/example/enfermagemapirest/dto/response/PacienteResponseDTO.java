package com.example.enfermagemapirest.dto.response;

public record PacienteResponseDTO(
        String cpf_pac,
        String nome_pac,
        Long cod_pac,
        String tel_pac,
        String cep_pac,
        String logra_pac,
        Long num_logra_pac,
        String compl_pac,
        String bairro_pac,
        String cidade_pac,
        String uf_pac,
        String rg_pac,
        String est_rg_pac,
        String nome_mae_pac,
        java.time.LocalDate data_nasc_pac
) {
}
