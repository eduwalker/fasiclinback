package com.example.enfermagemapirest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // Ajuste isso conforme necessário para produção
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Auth-Token"));
        configuration.setExposedHeaders(Arrays.asList("X-Auth-Token"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csfr -> csfr.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/userinfo").permitAll()
                        .requestMatchers(HttpMethod.GET,"/user/userinfo").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/fasiclin/paciente/info").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/fasiclin/anamnese").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/fasiclin/anamnese-status").hasRole("USER")
                        .requestMatchers(HttpMethod.GET,"/fasiclin/anamneses/supervisor").hasRole("USER")
                        .requestMatchers(HttpMethod.GET,"/fasiclin/anamneses/supervisor/page").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/fasiclin/anamnese/update-observations").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/fasiclin/anamnese/update-auth-pac").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/fasiclin/anamnese/save-to-prontuario").hasRole("USER")
                        .requestMatchers(HttpMethod.GET,"/fasiclin/anamneses/supervisor/aprovadas/page").hasRole("USER")
                        .requestMatchers(HttpMethod.GET,"/prontuario/download/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/prontuario/save").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT,"/fasiclin/anamnese/update-respostas").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT,"/fasiclin/anamnese/update-status").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/fasiclin/anamnese/respostas").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/fasiclin/anamneses").hasRole("USER")
                        .requestMatchers(HttpMethod.GET,"/fasiclin/anamneses/page").hasRole("USER")
                        .requestMatchers(HttpMethod.GET,"/fasiclin/anamnese/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/pacientes").permitAll()
                        .requestMatchers(HttpMethod.GET,"/anamnese/perguntas").permitAll()
                        .requestMatchers(HttpMethod.GET,"/anamnese/paciente/{cpf}").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/register/sup").permitAll()
                        .requestMatchers(HttpMethod.POST,"/dev/update-password").permitAll()
                        .requestMatchers(HttpMethod.GET,"/paciente").hasRole("USER")
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
