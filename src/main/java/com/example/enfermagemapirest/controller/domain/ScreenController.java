package com.example.enfermagemapirest.controller.domain;

import com.example.enfermagemapirest.data.entity.*;
import com.example.enfermagemapirest.data.repository.*;
import com.example.enfermagemapirest.dto.request.*;
import com.example.enfermagemapirest.dto.response.AnamneseAnswerResponse;
import com.example.enfermagemapirest.dto.response.AnamneseIdResponseDTO;
import com.example.enfermagemapirest.dto.response.AnamneseResponseDTO;
import com.example.enfermagemapirest.dto.response.PacienteResponseDTO;
import com.example.enfermagemapirest.services.AnamneseService;
import com.example.enfermagemapirest.services.TokenService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fasiclin")
public class ScreenController {



    @Autowired
    private AnamneseService anamneseService;

    @Autowired
    private AnamneseRepository anamneseRepository;

    @Autowired
    PerguntasRepository perguntasRepository;

    @Autowired
    RespostasRepository respostasRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/paciente/info")
    public ResponseEntity<?> getPacienteInfo(@RequestBody PacienteRequestDTO request, @RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.replace("Bearer ", "");

        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        }

        Optional<PacienteEntity> paciente = pacienteRepository.findByCpfPac(request.cpf());
        if (paciente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado.");
        }

        PacienteEntity found = paciente.get();
        PacienteResponseDTO pacienteDto = new PacienteResponseDTO(
                found.getCpfPac(),
                found.getNomePac(),
                found.getCodPac(),
                found.getTelPac(),
                found.getCepPac(),
                found.getLograPac(),
                found.getNumLograPac(),
                found.getComplPac(),
                found.getBairroPac(),
                found.getCidadePac(),
                found.getUfPac(),
                found.getRgPac(),
                found.getEstRgPac(),
                found.getNomeMaePac(),
                found.getDataNascPac()
        );
        return ResponseEntity.ok(pacienteDto);
    }


    @PostMapping("/anamnese")
    public ResponseEntity<?> createAnamnese(@RequestBody AnamneseDTO anamneseRequest, @RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.replace("Bearer ", "");

        // Verifica o token e obtém o username do profissional
        String username = tokenService.validateToken(token);
        if (username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        }


        ProfissionalEntity user = (ProfissionalEntity) profissionalRepository.findByUsername(username);

        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado.");
        }


        Optional<PacienteEntity> paciente = pacienteRepository.findByCpfPac(anamneseRequest.cpf());
        if (paciente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado.");
        }

        PacienteEntity found = paciente.get();


        // Cria a anamnese e configura os campos necessários
        AnamneseEntity anamnese = new AnamneseEntity();
        anamnese.setStatusAnamnese(StatusAnamnese.valueOf(anamneseRequest.status()));
        anamnese.setPaciente(found);
        anamnese.setProfissional(user);
        anamnese.setDataAnamnese(new Date());
        anamnese.setCpfPac(anamneseRequest.cpf());
        anamnese.setCodProf(user.getCodProf());
        anamnese.setStatusAnamneseFn(anamneseRequest.statusfn());

        // Salva a anamnese no banco de dados
        anamneseRepository.save(anamnese);
        AnamneseIdResponseDTO responseDTO = new AnamneseIdResponseDTO(anamnese.getAnamneseId());

        // Retorna uma resposta indicando sucesso
        return ResponseEntity.ok(responseDTO);
    }


    @PostMapping("/anamnese-status")
    public ResponseEntity<?> statusAnamnese(@RequestBody StatusAnamneseDTO statusAnamneseDTO, @RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.replace("Bearer ", "");


        String username = tokenService.validateToken(token);
        if (username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        }

        var anamneselOptional = anamneseRepository.findByAnamneseId(statusAnamneseDTO.idAnamnese());
        if (anamneselOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var anamneseOptional = anamneseRepository.findById(statusAnamneseDTO.idAnamnese());
        if (anamneseOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var anamnese = anamneseOptional.get();
        String statusA = statusAnamneseDTO.status();
        String statusB = statusAnamneseDTO.statusfn();

        try {
            anamnese.setStatusAnamnese(StatusAnamnese.valueOf(statusA));
            anamnese.setStatusAnamneseFn(statusB);
            anamneseRepository.save(anamnese);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Status inválido. Os status válidos são 'valid', 'invalid' ou 'approved'.");
        }


        return ResponseEntity.ok("Status alterado com sucesso!");
    }


    @PostMapping("/anamnese/respostas")
    public ResponseEntity<?> submitMultipleAnamneseRespostas(@RequestBody AnamneseRespostasDTO anamneseRespostasDTO,
                                                             @RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.replace("Bearer ", "");

        String username = tokenService.validateToken(token);
        if (username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        }

        // Verifica se a anamnese existe
        Optional<AnamneseEntity> anamneseOptional = anamneseRepository.findById(Long.valueOf(anamneseRespostasDTO.anamneseID()));
        if (!anamneseOptional.isPresent()) {
            return new ResponseEntity<>("Anamnese não encontrada.", HttpStatus.NOT_FOUND);
        }

        AnamneseEntity anamnese = anamneseOptional.get();
        List<RespostasEntity> respostasEntities = new ArrayList<>();


        for (PerguntaResposta pr : anamneseRespostasDTO.respostas()) {
            Optional<PerguntasEntity> perguntaOptional = perguntasRepository.findById(Long.valueOf(pr.questionID()));
            if (!perguntaOptional.isPresent()) {
                return new ResponseEntity<>("Pergunta com ID " + pr.questionID() + " não encontrada.", HttpStatus.NOT_FOUND);
            }
            PerguntasEntity pergunta = perguntaOptional.get();

            RespostasEntity resposta = new RespostasEntity();
            resposta.setAnamnese(anamnese);
            resposta.setPergunta(pergunta);
            resposta.setAnswerText(pr.answer());
            respostasEntities.add(resposta);
        }

        respostasRepository.saveAll(respostasEntities);

        return ResponseEntity.ok(new AnamneseAnswerResponse(true, "Respostas salvas com sucesso.", anamnese.getAnamneseId()));
    }


    @GetMapping("/anamneses")
    public ResponseEntity<?> getAnamnesesByUser(@RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.replace("Bearer ", "");
        String username = tokenService.validateToken(token);

        if (username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        }

        try {
            List<AnamneseResponseDTO> anamneses = anamneseService.getAnamnesesByUser(username);
            return ResponseEntity.ok(anamneses);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }


    @GetMapping("/anamnese/{id}")
    public ResponseEntity<?> getAnamnese(@PathVariable Long id, @RequestHeader("Authorization") String bearerToken) {
        String username = tokenService.validateToken(bearerToken.replace("Bearer ", ""));
        if (username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        }

        Optional<AnamneseEntity> anamneseOptional = anamneseRepository.findById(id);
        if (anamneseOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AnamneseEntity anamnese = anamneseOptional.get();
        List<RespostasEntity> respostas = respostasRepository.findByAnamnese(anamnese);

        List<Map<String, String>> perguntasRespostas = respostas.stream()
                .map(resposta -> Map.of(
                        "resposta", resposta.getAnswerText(),
                        "pergunta", resposta.getPergunta().getText()
                ))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("anamneseId", anamnese.getAnamneseId());
        response.put("dataAnamnese", anamnese.getDataAnamnese());
        response.put("paciente", Map.of(
                "nome_pac", anamnese.getPaciente().getNomePac(),
                "data_nasc_pac", anamnese.getPaciente().getDataNascPac(),
                "cpf_pac", anamnese.getPaciente().getCpfPac()
        ));
        response.put("profissional", Map.of(
                "nome", anamnese.getProfissional().getNomeProf(),
                "especialidade", "Clínica Geral"  // Assumindo valor estático por simplificação
        ));
        response.put("perguntasRespostas", perguntasRespostas);
        response.put("statusAnamnese", anamnese.getStatusAnamnese());

        return ResponseEntity.ok(response);
    }



    @GetMapping("/anamneses/supervisor")
    public ResponseEntity<?> getAnamnesesBySupervisor(@RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.replace("Bearer ", "");
        String username = tokenService.validateToken(token);

        if (username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        }

        // Obter o profissional pelo username
        ProfissionalEntity supervisor = (ProfissionalEntity) profissionalRepository.findByUsername(username);
        if (supervisor == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado.");
        }

        try {
            List<AnamneseResponseDTO> anamneses = anamneseService.getAnamnesesBySupervisor(supervisor.getCodProf());
            return ResponseEntity.ok(anamneses);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/anamnese/update-observations")
    public ResponseEntity<?> updateObservations(@RequestBody AnamneseObsDTO request, @RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.replace("Bearer ", "");

        String username = tokenService.validateToken(token);
        if (username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        }

        Optional<AnamneseEntity> anamneseOptional = anamneseRepository.findById(request.anamneseId());
        if (anamneseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anamnese não encontrada.");
        }

        AnamneseEntity anamnese = anamneseOptional.get();
        anamnese.setObservacoes(request.observacoes());
        anamnese.setStatusAnamneseFn(request.status());

        anamneseRepository.save(anamnese);
        return ResponseEntity.ok("Status e observações atualizados com sucesso.");
    }


    @PutMapping("/anamnese/update-respostas")
    public ResponseEntity<?> updateRespostas(@RequestBody AnamneseRespostasDTO request, @RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.replace("Bearer ", "");

        String username = tokenService.validateToken(token);
        if (username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        }

        try {
            anamneseService.updateRespostas(request);
            return ResponseEntity.ok("Respostas da anamnese atualizadas com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID de pergunta desconhecido.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar respostas da anamnese.");
        }
    }


}






