package com.example.enfermagemnew.repository.anamnese;

import com.example.enfermagemnew.entity.anamnese.RespostasAnamneseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespostasAnamneseRepository extends JpaRepository<RespostasAnamneseEntity, Long> {
}
