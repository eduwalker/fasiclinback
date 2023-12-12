package com.example.enfermagemnew.entity;

import com.example.enfermagemnew.controller.user.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "userenf")
@Data
@Table(name = "userenf")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_user")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private long id_user;

    @Column(name = "nome_user")
    private String nome_user;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password_user")
    private String password_user;

    @Column(name = "token_user")
    private String token_user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_login")
    private UserRole role_login;

    @Column(name = "cod_prof")
    private Long codProf;
    @Column(name = "sup_prof")
    private Long cod_sup;




    public UserEntity(String username, String encryptedPassword, UserRole role) {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role_login == UserRole.ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password_user;
    }

    @Override
    public String getUsername() {
        return username;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Métodos adicionais, se necessário, para manipular a entidade Profissional associada
}
