package com.example.enfermagemapirest.controller.user;

import com.example.enfermagemapirest.data.repository.ProfissionalRepository;
import com.example.enfermagemapirest.dto.request.PasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev")
public class DevController {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody PasswordDTO passwordDTO) {
        var profissionalOptional = profissionalRepository.findByCodProf(passwordDTO.codProf());
        if (profissionalOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(passwordDTO.newPass());

        var profissional = profissionalOptional.get();
        profissional.setPassword(encryptedPassword);
        profissionalRepository.save(profissional);

        return ResponseEntity.ok("Senha atualizada com sucesso.");
    }
}
