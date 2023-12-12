package com.example.enfermagemnew.services;

import com.example.enfermagemnew.dto.anamnese.AnamneseDTO;
import com.example.enfermagemnew.dto.anamnese.RespostasAnamneseDTO;
import com.example.enfermagemnew.entity.UserEntity;
import com.example.enfermagemnew.entity.anamnese.AnamneseEntity;
import com.example.enfermagemnew.entity.anamnese.PerguntasAnamneseEntity;
import com.example.enfermagemnew.entity.anamnese.RespostasAnamneseEntity;
import com.example.enfermagemnew.repository.PacienteRepository;
import com.example.enfermagemnew.repository.UserRepository;
import com.example.enfermagemnew.repository.anamnese.AnamneseRepository;
import com.example.enfermagemnew.repository.anamnese.PerguntasAnamneseRepository;
import com.example.enfermagemnew.repository.anamnese.RespostasAnamneseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnamneseService {

    @Autowired
    private AnamneseRepository anamneseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private PerguntasAnamneseRepository perguntasAnamneseRepository;
    @Autowired
    private RespostasAnamneseRepository RespostasAnamneseRepository;



    @Transactional
    public AnamneseEntity criarChaveAnamnese(AnamneseDTO anamneseDto) {
        UserEntity user = userRepository.findById(anamneseDto.idUser())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Obtém o código do supervisor
        Long codSupervisor = userRepository.findByCodProf(anamneseDto.idSupervisor())
                .orElseThrow(() -> new RuntimeException("Supervisor não encontrado"))
                .getCodProf();

        AnamneseEntity anamnese = new AnamneseEntity();
        anamnese.setIdUser(user);

        // Definir codSupervisor em vez do supervisor diretamente
        anamnese.setSupervisor(codSupervisor);

        anamnese.setPaciente(pacienteRepository.findByCpfPac(anamneseDto.cpfPaciente()).orElse(null));
        anamnese.setDataPreenchimento(anamneseDto.dataPreenchimento());

        anamneseRepository.save(anamnese);
        return anamnese;
    }


    @Transactional
    public void salvarRespostasAnamnese(List<RespostasAnamneseDTO> respostasDto, Long idAnamnese) {
        Optional<AnamneseEntity> anamneseOptional = anamneseRepository.findById(idAnamnese);
        if (!anamneseOptional.isPresent()) {
            throw new RuntimeException("Anamnese não encontrada");
        }
        AnamneseEntity anamnese = anamneseOptional.get();

        for (RespostasAnamneseDTO respostaDto : respostasDto) {
            RespostasAnamneseEntity respostaEntity = new RespostasAnamneseEntity();
            respostaEntity.setAnamnese(anamnese);

            Optional<PerguntasAnamneseEntity> perguntaOptional = perguntasAnamneseRepository.findByIdPergunta(respostaDto.idPergunta());
            if (!perguntaOptional.isPresent()) {
                throw new RuntimeException("Pergunta de anamnese não encontrada");
            }
            respostaEntity.setPergunta(perguntaOptional.get());

            respostaEntity.setResposta(respostaDto.resposta());

            RespostasAnamneseRepository.save(respostaEntity);
        }
    }

    public List<PerguntaRespostaDTO> buscarPerguntasRespostasPorAnamnese(Long idAnamnese) {
        List<RespostasAnamneseEntity> respostas = RespostasAnamneseRepository.findByIdAnamnese(idAnamnese);
        return respostas.stream().map(this::converterParaPerguntaRespostaDto).collect(Collectors.toList());
    }

    private PerguntaRespostaDTO converterParaPerguntaRespostaDto(RespostasAnamneseEntity resposta) {
        PerguntaRespostaDTO dto = new PerguntaRespostaDTO();
        dto.setTextoPergunta(resposta.getPergunta().getTextoPergunta());
        dto.setResposta(resposta.getResposta());
        // Mapeie outras propriedades necessárias
        return dto;
    }
}



