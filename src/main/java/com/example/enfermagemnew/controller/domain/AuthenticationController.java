package com.example.enfermagemnew.controller.domain;

import com.example.enfermagemnew.controller.user.UserRole;
import com.example.enfermagemnew.dto.*;
import com.example.enfermagemnew.entity.ProfissionalEntity;
import com.example.enfermagemnew.entity.UserEntity;
import com.example.enfermagemnew.repository.ProfissionalRepository;
import com.example.enfermagemnew.repository.UserRepository;
import com.example.enfermagemnew.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ProfissionalRepository profissionalRepository;



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
            var auth = authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((UserEntity) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (repository.findByUsername(data.username()) != null) {
            return ResponseEntity.badRequest().body("Nome de usuário já existe.");
        }
        if (!verificaProfissionalExistente(Long.valueOf(data.codProf()))) {
            return ResponseEntity.badRequest().body("Código de profissional inválido.");
        }
        if (!verificaSupProfissionalExistente(Long.valueOf(data.supProf()))) {
            return ResponseEntity.badRequest().body("O código do Supervisor não está vinculado ao seu registro...");
        }


        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        Long codProf = Long.valueOf(data.codProf());
        Long codSup = Long.valueOf(data.supProf());
        UserEntity newUser = new UserEntity();
        newUser.setNome_user(data.nome());
        newUser.setUsername(data.username());
        newUser.setPassword_user(encryptedPassword);
        newUser.setRole_login(data.role());
        newUser.setCodProf(codProf); // Definindo o código do profissional
        newUser.setCod_sup(codSup); // Definindo o código do supervisor

        this.repository.save(newUser);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/register/sup")
    public ResponseEntity<String> registersup(@RequestBody @Valid RegisterSupDTO data) {
        if (repository.findByUsername(data.username())!=null) {
            return ResponseEntity.badRequest().body("Nome de usuário já existe.");
        }

        ProfissionalEntity profissional = profissionalRepository.findByCodProf(Long.valueOf(data.codProf()));
        if (profissional == null) {
            return ResponseEntity.badRequest().body("Código de profissional inválido.");
        }

        // Converte a role recebida para o enum UserRole
        String roleStr = data.role().toString().toUpperCase(); // Converte a role para maiúsculas
        UserRole role;

        try {
            role = UserRole.valueOf(roleStr); // Converte a string para o enum UserRole
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Role inválida.");
        }

        // Verifica se o tipo_prof é 2 ou 3 e se o papel pretendido é ADMIN
        if ((profissional.getTipoProf() == 2 || profissional.getTipoProf() == 3) && role == UserRole.ADMIN) {
            // O papel é ADMIN e o tipo_prof é permitido
        } else {
            // O papel não é ADMIN ou o tipo_prof não é permitido
            return ResponseEntity.badRequest().body("Operação não permitida. Apenas profissionais com tipo 2 ou 3 podem ser cadastrados como ADMIN.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        UserEntity newUser = new UserEntity();
        newUser.setNome_user(data.nome());
        newUser.setUsername(data.username());
        newUser.setPassword_user(encryptedPassword);
        newUser.setRole_login(role); // Configura a role convertida
        newUser.setCodProf(profissional.getCodProf()); // Definindo o código do profissional

        repository.save(newUser);
        return ResponseEntity.ok("Supervisor cadastrado com sucesso.");
    }


    public boolean verificaProfissionalExistente(Long codProf) {
        return profissionalRepository.findById(codProf).isPresent();
    }
    public boolean verificaSupProfissionalExistente(Long codSup) {
        return profissionalRepository.findById(codSup).isPresent();
    }

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.replace("Bearer ", "");
        String username = tokenService.validateToken(token);

        if(username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        }

        UserEntity user = (UserEntity) repository.findByUsername(username);

        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado.");
        }

        UserResponseDTO userDto = new UserResponseDTO(user.getNome_user(), user.getUsername(),user.getId_user(), user.getCodProf(), user.getCod_sup());
        return ResponseEntity.ok(userDto);
    }



}


