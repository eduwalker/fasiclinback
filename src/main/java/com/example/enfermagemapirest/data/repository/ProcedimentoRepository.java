package com.example.enfermagemapirest.data.repository;
import com.example.enfermagemapirest.data.entity.ProcedimentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcedimentoRepository extends JpaRepository<ProcedimentoEntity, Long> {
    Optional<ProcedimentoEntity> findByCodProced(Long codProced);
}