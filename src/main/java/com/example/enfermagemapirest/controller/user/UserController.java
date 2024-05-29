package com.example.enfermagemapirest.controller.user;

import com.example.enfermagemapirest.data.entity.ProfissionalEntity;
import com.example.enfermagemapirest.data.repository.ProfissionalRepository;
import com.example.enfermagemapirest.dto.response.UserResponseDTO;
import com.example.enfermagemapirest.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.replace("Bearer ", "");
        String username = tokenService.validateToken(token);

        if(username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        }

        ProfissionalEntity user = (ProfissionalEntity) profissionalRepository.findByUsername(username);

        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado.");
        }

        UserResponseDTO userDto = new UserResponseDTO(user.getNomeProf(), user.getCodProf(), user.getSupProf(), user.getTipoProf(), user.getStatusProf(), user.getConsProf() );
        return ResponseEntity.ok(userDto);
    }

}
