package com.example.enfermagemapirest.controller.domain;

import com.example.enfermagemapirest.data.entity.AnamneseEntity;
import com.example.enfermagemapirest.data.entity.ProntuarioEntity;
import com.example.enfermagemapirest.data.repository.AnamneseRepository;
import com.example.enfermagemapirest.data.repository.ProntuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/prontuario")
public class ProntuarioController {

    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @Autowired
    private AnamneseRepository anamneseRepository;

    @GetMapping("/download/{link}")
    public ResponseEntity<?> getAnamneseByLink(@PathVariable String link) {
        // Localiza o prontuário pelo link do procedimento
        Optional<ProntuarioEntity> prontuarioOptional = prontuarioRepository.findByLinkProced(link);
        if (prontuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ProntuarioEntity prontuario = prontuarioOptional.get();

        // Localiza a anamnese pelo ID
        Long anamneseId = extractAnamneseIdFromLink(link);
        Optional<AnamneseEntity> anamneseOptional = anamneseRepository.findById(anamneseId);
        if (anamneseOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AnamneseEntity anamnese = anamneseOptional.get();

        // Aqui você pode retornar a anamnese ou o PDF, conforme necessário
        // Por enquanto, estamos retornando a anamnese como exemplo
        return ResponseEntity.ok(anamnese);
    }

    private Long extractAnamneseIdFromLink(String link) {
        // Extrair o ID da anamnese do link (supondo que esteja no formato esperado)
        String idPart = link.replaceAll("\\D", "");
        return Long.parseLong(idPart.substring(5, 6)); // Supondo que o ID seja o 6º dígito
    }
}
