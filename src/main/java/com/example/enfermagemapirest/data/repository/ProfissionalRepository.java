package com.example.enfermagemapirest.data.repository;

import com.example.enfermagemapirest.data.entity.ProfissionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfissionalRepository extends JpaRepository<ProfissionalEntity, Long> {

    UserDetails findByUsername(String username);

    Optional<ProfissionalEntity> findByCodProf(Long username);

}
