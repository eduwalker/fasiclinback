package com.example.enfermagemapirest.services;

import com.example.enfermagemapirest.data.entity.ProfissionalEntity;
import com.example.enfermagemapirest.data.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizathionService implements UserDetailsService {

    @Autowired
    ProfissionalRepository profissionalRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Long codProf = Long.parseLong(username);
        return profissionalRepository.findByCodProf(codProf)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}

