package com.pfc.thindesk.service;

import com.pfc.thindesk.entity.Usuario;
import com.pfc.thindesk.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private TokenService tokenService; // se for gerar JWT depois

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
        return usuario; // Usuario já implementa UserDetails
    }

    public String login(String username, String password) {

        System.out.println("tentaram acessar com " + username + " e " + password);

        Optional<Usuario> usuario = usuarioRepository.findByUsuario(username);
        if (!usuario.isPresent()) {
            System.out.println("usuario não encontrado");
            return null;
        } else {
            if (!passwordEncoder.matches(password, usuario.get().getSenha())) {
                System.out.println("senha incorreta");
                return null;
            } else {

                String token = tokenService.gerarToken(usuario.get());
                if (token == null) {
                    System.out.println("token não foi gerado");
                }

                System.out.println("token: " + token);

                return token;

            }
        }



    }





}
