package com.example.enfermagemapirest.data.repository;

import com.example.enfermagemapirest.data.entity.AnamneseEntity;
import com.example.enfermagemapirest.data.entity.ProfissionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnamneseRepository extends JpaRepository<AnamneseEntity, Long> {


    Optional<AnamneseEntity> findByAnamneseId(Long anamnese);

    @Query("SELECT a FROM AnamneseEntity a JOIN a.profissional p WHERE p.username = :username")
    List<AnamneseEntity> findByProfissionalUsername(@Param("username") String username);

}