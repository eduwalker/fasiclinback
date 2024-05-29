package com.example.enfermagemapirest.data.repository;

import com.example.enfermagemapirest.data.entity.AnamneseEntity;
import com.example.enfermagemapirest.data.entity.PerguntasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerguntasRepository extends JpaRepository<PerguntasEntity, Long> {

    Optional<PerguntasEntity> findById(Long anamnese);
}
