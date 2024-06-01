package com.example.enfermagemapirest.data.repository;

import com.example.enfermagemapirest.data.entity.AnamneseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnamneseRepository extends JpaRepository<AnamneseEntity, Long> {

    Optional<AnamneseEntity> findByAnamneseId(Long anamnese);

    @Query("SELECT a FROM AnamneseEntity a JOIN a.profissional p WHERE p.username = :username ORDER BY a.dataAnamnese DESC")
    List<AnamneseEntity> findByProfissionalUsername(@Param("username") String username);

    @Query("SELECT a FROM AnamneseEntity a WHERE a.profissional.supProf = :codSup OR a.profissional.codProf = :codSup ORDER BY a.dataAnamnese DESC")
    List<AnamneseEntity> findBySupervisor(@Param("codSup") Long codSup);

    @Query("SELECT a FROM AnamneseEntity a JOIN a.profissional p WHERE p.username = :username ORDER BY a.dataAnamnese DESC")
    Page<AnamneseEntity> findByProfissionalUsername(@Param("username") String username, Pageable pageable);

    @Query("SELECT a FROM AnamneseEntity a WHERE a.profissional.supProf = :codSup OR a.profissional.codProf = :codSup ORDER BY a.dataAnamnese DESC")
    Page<AnamneseEntity> findBySupervisor(@Param("codSup") Long codSup, Pageable pageable);
}
