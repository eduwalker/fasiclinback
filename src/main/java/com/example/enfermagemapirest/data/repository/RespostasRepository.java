package com.example.enfermagemapirest.data.repository;

import com.example.enfermagemapirest.data.entity.AnamneseEntity;
import com.example.enfermagemapirest.data.entity.PerguntasEntity;
import com.example.enfermagemapirest.data.entity.RespostasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RespostasRepository extends JpaRepository<RespostasEntity, Long> {


    @Query("SELECT r FROM RespostasEntity r WHERE r.anamnese = :anamnese")
    List<RespostasEntity> findByAnamnese(AnamneseEntity anamnese);

    Optional<RespostasEntity> findByAnamneseAndPergunta(AnamneseEntity anamnese, PerguntasEntity pergunta);
}
