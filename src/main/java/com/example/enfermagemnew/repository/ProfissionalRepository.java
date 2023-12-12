package com.example.enfermagemnew.repository;

import com.example.enfermagemnew.entity.ProfissionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfissionalRepository extends JpaRepository<ProfissionalEntity, Long> {

    ProfissionalEntity findByCodProf(Long codProf);

}
