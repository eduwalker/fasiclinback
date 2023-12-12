package com.example.enfermagemnew.controller.domain;

import com.example.enfermagemnew.dto.anamnese.AnamneseDTO;
import com.example.enfermagemnew.dto.anamnese.PerguntasResponseDTO;
import com.example.enfermagemnew.dto.anamnese.RespostasAnamneseDTO;
import com.example.enfermagemnew.entity.PacienteEntity;
import com.example.enfermagemnew.entity.anamnese.AnamneseEntity;
import com.example.enfermagemnew.entity.anamnese.RespostasAnamneseEntity;
import com.example.enfermagemnew.repository.PacienteRepository;
import com.example.enfermagemnew.repository.anamnese.PerguntasAnamneseRepository;
import com.example.enfermagemnew.services.AnamneseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("anamnese")
public class AnamneseController {

    @Autowired
    private PerguntasAnamneseRepository perguntasRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private AnamneseService anamneseService;


    @GetMapping("/perguntas")
    public ResponseEntity getAllPerguntas(){
        List<PerguntasResponseDTO> pacientList = this.perguntasRepository.findAll().stream().map(PerguntasResponseDTO::new).toList();
        return ResponseEntity.ok(pacientList);
    }

    @GetMapping("/paciente/{cpf}")
    public ResponseEntity getPacienteCpf(@PathVariable String cpf){
        Optional<PacienteEntity> paciente = pacienteRepository.findByCpfPac(cpf);
        if (paciente.isPresent()) {

            return ResponseEntity.ok(paciente.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado!");
        }
    }

    @PostMapping("/chave")
    public ResponseEntity<AnamneseEntity> criarChaveAnamnese(@RequestBody AnamneseDTO anamneseDto) {
        AnamneseEntity anamnese = anamneseService.criarChaveAnamnese(anamneseDto);
        return ResponseEntity.ok(anamnese);
    }

    @PostMapping("/respostas")
    public ResponseEntity<?> criarResposta(@RequestBody List<RespostasAnamneseDTO> respostasDto, @RequestParam Long idAnamnese) {
        anamneseService.salvarRespostasAnamnese(respostasDto, idAnamnese);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/usuario/{idUser}")
//    public ResponseEntity<List<AnamneseDTO>> buscarAnamnesesPorUsuario(@PathVariable Long idUser) {
//        try {
//            List<AnamneseDTO> anamneses = anamneseService.buscarAnamnesesPorUsuario(idUser);
//            return ResponseEntity.ok(anamneses);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }


}
