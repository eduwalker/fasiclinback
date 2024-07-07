package com.example.enfermagemapirest.dto.request;

import java.util.Date;

public record AnamneseProntuarioDTO(
        String cpfPac,
        Long codEspec,
        Long codProf,
        Long codProced,
        Date dataProced,
        String descrProced,
        String linkProced,
        int authVisPac
) {
}