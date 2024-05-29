package com.example.enfermagemapirest.services;

import com.example.enfermagemapirest.data.entity.AnamneseEntity;
import com.example.enfermagemapirest.data.entity.PacienteEntity;
import com.example.enfermagemapirest.data.entity.ProfissionalEntity;
import com.example.enfermagemapirest.data.repository.AnamneseRepository;
import com.example.enfermagemapirest.data.repository.ProfissionalRepository;
import com.example.enfermagemapirest.dto.response.AnamneseListResponseDTO;
import com.example.enfermagemapirest.dto.response.AnamneseResponseDTO;
import com.example.enfermagemapirest.dto.response.PacienteResponseDTO;
import com.example.enfermagemapirest.dto.response.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnamneseService {

    @Autowired
    private AnamneseRepository anamneseRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    public List<AnamneseResponseDTO> getAnamnesesByUser(String username) {
        ProfissionalEntity user = (ProfissionalEntity) profissionalRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        List<AnamneseEntity> anamneses = anamneseRepository.findByProfissionalUsername(username);
        return anamneses.stream().map(anamnese -> {
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
            UserResponseDTO profissionalDto = new UserResponseDTO(
                    user.getNomeProf(),
                    user.getCodProf(),
                    user.getSupProf(),
                    user.getTipoProf(),
                    user.getStatusProf(),
                    user.getConsProf()
            );
            return new AnamneseResponseDTO(
                    anamnese.getAnamneseId(),
                    anamnese.getCodProf(),
                    anamnese.getCpfPac(),
                    anamnese.getDataAnamnese(),
                    anamnese.getStatusAnamnese().name(),
                    anamnese.getStatusAnamneseFn(),
                    pacienteDto
            );
        }).collect(Collectors.toList());
    }


}