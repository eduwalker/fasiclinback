package com.example.enfermagemnew.repository;

import com.example.enfermagemnew.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserDetails findByUsername(String username);
    Optional<UserEntity> findByCodProf(Long codProf);

}
