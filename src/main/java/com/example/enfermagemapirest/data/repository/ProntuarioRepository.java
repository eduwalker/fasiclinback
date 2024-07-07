package com.example.enfermagemapirest.data.repository;

import com.example.enfermagemapirest.data.entity.ProntuarioEntity;
import com.example.enfermagemapirest.data.entity.ProntuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProntuarioRepository extends JpaRepository<ProntuarioEntity, Long> {
    Optional<ProntuarioEntity> findByLinkProced(String linkProced);
}
