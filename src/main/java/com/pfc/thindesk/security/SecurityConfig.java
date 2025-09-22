package com.pfc.thindesk.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF desabilitado
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/cadastro-cliente","/login", "/css/**", "/js/**", "/images/**", "/favicon.ico","/logout","/error").permitAll()
                        // Somente FUNCIONARIO pode acessar /chamados e subrotas
                        .requestMatchers("/chamados/**").hasRole("FUNCIONARIO")
                        .requestMatchers("/clientes/**").hasRole("FUNCIONARIO")
                        .requestMatchers("/").hasRole("FUNCIONARIO")
                        // Somente CLIENTE pode acessar /home_client e subrotas
                        .requestMatchers("/home_client/**").hasRole("CLIENTE")
                        .anyRequest().permitAll() // todas as outras rotas precisam de autenticação
                ).logout(logout -> logout
                        .logoutUrl("/logout") // ou outra URL que você quiser para logout
                        .deleteCookies("Authorization") // nome do cookie que você quer apagar
                        .logoutSuccessUrl("/login") // redireciona após logout
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // sem sessão
                );

        // Adiciona o filtro customizado antes do UsernamePasswordAuthenticationFilter
        http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
