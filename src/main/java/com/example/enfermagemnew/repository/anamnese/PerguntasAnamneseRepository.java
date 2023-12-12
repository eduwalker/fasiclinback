package com.example.enfermagemnew.repository.anamnese;

import com.example.enfermagemnew.entity.UserEntity;
import com.example.enfermagemnew.entity.anamnese.PerguntasAnamneseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerguntasAnamneseRepository extends JpaRepository<PerguntasAnamneseEntity, Long> {

    Optional<PerguntasAnamneseEntity> findByIdPergunta(Long idPergunta);
}
