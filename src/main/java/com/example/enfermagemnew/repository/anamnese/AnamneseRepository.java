package com.example.enfermagemnew.repository.anamnese;

import com.example.enfermagemnew.entity.anamnese.AnamneseEntity;
import com.example.enfermagemnew.entity.anamnese.PerguntasAnamneseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnamneseRepository extends JpaRepository<AnamneseEntity, Long> {

    List<AnamneseEntity> findByIdUser(Long idUser);
}
