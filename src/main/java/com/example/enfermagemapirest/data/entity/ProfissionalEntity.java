package com.example.enfermagemapirest.data.entity;

import com.example.enfermagemapirest.controller.user.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity(name = "profissional")
@Table(name = "profissional")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "codProf")
public class ProfissionalEntity implements UserDetails {

    @Id
    @Column(name = "cod_prof", nullable = false)
    private Long codProf;

    @Column(name = "username")
    private String username;

    @Column(name = "nome_prof", nullable = false)
    private String nomeProf;


    @Column(name = "tipo_prof", nullable = false)
    private Long tipoProf;

    @Column(name = "sup_prof")
    private Long supProf;

    @Column(name = "status_prof", nullable = false)
    private Integer statusProf;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "cons_prof")
    private String consProf;



    public ProfissionalEntity(String username, String encryptedPassword, UserRole role) {
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.tipoProf == UserRole.ADMIN.getRole())
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
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
}
