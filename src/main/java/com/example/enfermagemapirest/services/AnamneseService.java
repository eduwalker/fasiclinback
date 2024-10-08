package com.example.enfermagemapirest.services;

import com.example.enfermagemapirest.data.entity.*;
import com.example.enfermagemapirest.data.repository.AnamneseRepository;
import com.example.enfermagemapirest.data.repository.PerguntasRepository;
import com.example.enfermagemapirest.data.repository.ProfissionalRepository;
import com.example.enfermagemapirest.data.repository.RespostasRepository;
import com.example.enfermagemapirest.dto.request.AnamneseRespostasDTO;
import com.example.enfermagemapirest.dto.request.PerguntaResposta;
import com.example.enfermagemapirest.dto.response.AnamneseResponseDTO;
import com.example.enfermagemapirest.dto.response.PacienteResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnamneseService {

    @Autowired
    private AnamneseRepository anamneseRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private RespostasRepository respostaRepository;

    @Autowired
    private PerguntasRepository perguntaRepository;

    public List<AnamneseResponseDTO> getAnamnesesByUser(String username) {
        ProfissionalEntity user = (ProfissionalEntity) profissionalRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        List<AnamneseEntity> anamneses = anamneseRepository.findByProfissionalUsername(username);
        return mapAnamnesesToDTOs(anamneses, user);
    }

    public Page<AnamneseResponseDTO> getPagedAnamnesesByUser(String username, Pageable pageable) {
        ProfissionalEntity user = (ProfissionalEntity) profissionalRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        Page<AnamneseEntity> anamneses = anamneseRepository.findByProfissionalUsername(username, pageable);
        return anamneses.map(anamnese -> mapAnamneseToDTO(anamnese, user));
    }

    public List<AnamneseResponseDTO> getAnamnesesBySupervisor(Long codSup) {
        List<AnamneseEntity> anamneses = anamneseRepository.findBySupervisor(codSup);
        ProfissionalEntity supervisor = profissionalRepository.findByCodProf(codSup).orElseThrow(() -> new RuntimeException("Supervisor não encontrado."));

        List<AnamneseEntity> filteredAnamneses = anamneses.stream()
                .filter(anamnese -> "Analise".equals(anamnese.getStatusAnamneseFn()))
                .collect(Collectors.toList());

        return mapAnamnesesToDTOs(filteredAnamneses, supervisor);
    }

    public Page<AnamneseResponseDTO> getPagedAnamnesesBySupervisor(Long codSup, Pageable pageable) {
        ProfissionalEntity supervisor = profissionalRepository.findByCodProf(codSup).orElseThrow(() -> new RuntimeException("Supervisor não encontrado."));

        Page<AnamneseEntity> anamneses = anamneseRepository.findBySupervisor(codSup, pageable);
        List<AnamneseEntity> filteredAnamneses = anamneses.stream()
                .filter(anamnese -> "Analise".equals(anamnese.getStatusAnamneseFn()))
                .collect(Collectors.toList());

        return new PageImpl<>(filteredAnamneses.stream()
                .map(anamnese -> mapAnamneseToDTO(anamnese, supervisor))
                .collect(Collectors.toList()), pageable, filteredAnamneses.size());
    }

    private List<AnamneseResponseDTO> mapAnamnesesToDTOs(List<AnamneseEntity> anamneses, ProfissionalEntity user) {
        return anamneses.stream().map(anamnese -> mapAnamneseToDTO(anamnese, user)).collect(Collectors.toList());
    }

    private AnamneseResponseDTO mapAnamneseToDTO(AnamneseEntity anamnese, ProfissionalEntity user) {
        PacienteEntity paciente = anamnese.getPaciente();
        PacienteResponseDTO pacienteDto = new PacienteResponseDTO(
                paciente.getCpfPac(),
                paciente.getNomePac(),
                paciente.getCodPac(),
                paciente.getTelPac(),
                paciente.getCepPac(),
                paciente.getLograPac(),
                paciente.getNumLograPac(),
                paciente.getComplPac(),
                paciente.getBairroPac(),
                paciente.getCidadePac(),
                paciente.getUfPac(),
                paciente.getRgPac(),
                paciente.getEstRgPac(),
                paciente.getNomeMaePac(),
                paciente.getDataNascPac()
        );
        return new AnamneseResponseDTO(
                anamnese.getAnamneseId(),
                anamnese.getCodProf(),
                anamnese.getCpfPac(),
                anamnese.getDataAnamnese(),
                anamnese.getStatusAnamnese().name(),
                anamnese.getStatusAnamneseFn(),
                anamnese.getObservacoes(),
                anamnese.getProfissional().getNomeProf(),
                anamnese.getAuth_pac(),
                pacienteDto
        );
    }

    public void updateRespostas(AnamneseRespostasDTO request) {
        Optional<AnamneseEntity> optionalAnamnese = anamneseRepository.findById(Long.valueOf(request.anamneseID()));
        if (optionalAnamnese.isPresent()) {
            AnamneseEntity anamnese = optionalAnamnese.get();

            for (PerguntaResposta respostaDTO : request.respostas()) {
                updateOrCreateResposta(anamnese, respostaDTO);
            }

            anamnese.setStatusAnamneseFn("Analise");

            anamneseRepository.save(anamnese);
        } else {
            throw new RuntimeException("Anamnese não encontrada.");
        }
    }

    private void updateOrCreateResposta(AnamneseEntity anamnese, PerguntaResposta respostaDTO) {
        Optional<PerguntasEntity> optionalPergunta = perguntaRepository.findById((long) respostaDTO.questionID());
        if (optionalPergunta.isPresent()) {
            PerguntasEntity pergunta = optionalPergunta.get();

            Optional<RespostasEntity> existingResposta = respostaRepository.findByAnamneseAndPergunta(anamnese, pergunta);
            RespostasEntity resposta;
            if (existingResposta.isPresent()) {
                resposta = existingResposta.get();
                resposta.setAnswerText(respostaDTO.answer());
            } else {
                resposta = new RespostasEntity();
                resposta.setAnamnese(anamnese);
                resposta.setPergunta(pergunta);
                resposta.setAnswerText(respostaDTO.answer());
            }
            respostaRepository.save(resposta);
        } else {
            throw new IllegalArgumentException("ID de pergunta desconhecido: " + respostaDTO.questionID());
        }
    }

    public List<AnamneseResponseDTO> getApprovedAnamnesesBySupervisor(Long codSup) {
        List<AnamneseEntity> anamneses = anamneseRepository.findApprovedBySupervisor(codSup);
        ProfissionalEntity supervisor = profissionalRepository.findByCodProf(codSup).orElseThrow(() -> new RuntimeException("Supervisor não encontrado."));
        return mapAnamnesesToDTOs(anamneses, supervisor);
    }

    public Page<AnamneseResponseDTO> getPagedApprovedAnamnesesBySupervisor(Long codSup, Pageable pageable) {
        ProfissionalEntity supervisor = profissionalRepository.findByCodProf(codSup).orElseThrow(() -> new RuntimeException("Supervisor não encontrado."));
        Page<AnamneseEntity> anamneses = anamneseRepository.findApprovedBySupervisor(codSup, pageable);
        return anamneses.map(anamnese -> mapAnamneseToDTO(anamnese, supervisor));
    }
}
