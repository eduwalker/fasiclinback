package com.example.enfermagemnew.repository;

import com.example.enfermagemnew.entity.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PacienteRepository extends JpaRepository<PacienteEntity,String> {

        Optional<PacienteEntity> findByCpfPac(String cpf);

}
