package com.pfc.thindesk.service;

import com.pfc.thindesk.entity.Cliente;
import com.pfc.thindesk.entity.Funcionario;
import com.pfc.thindesk.entity.Role;
import com.pfc.thindesk.entity.Usuario;
import com.pfc.thindesk.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Salva um usuário (Cliente ou Funcionario) no MongoDB.
     * Criptografa a senha antes de salvar.
     */
    public String salvarUsuario(Usuario usuario) {

        Optional<Usuario> jaexiste = usuarioRepository.findByUsuario(usuario.getUsuario());
        if (jaexiste.isPresent()) {
            return null;
        }

        // criptografa a senha
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        // garante que o usuário tenha role definida (opcional)
        if (usuario.getRole() == null) {
            // se for Cliente, atribui CLIENTE
            if (usuario instanceof com.pfc.thindesk.entity.Cliente) {
                usuario.setRole(Role.ROLE_CLIENTE);
            }
            // se for Funcionario, atribui FUNCIONARIO
            else if (usuario instanceof com.pfc.thindesk.entity.Funcionario) {
                usuario.setRole(Role.ROLE_FUNCIONARIO);
            }
        }

        // salva no MongoDB
        usuarioRepository.save(usuario);
        return ("ok");
    }

    public void criarFuncionarioPadrao() {

        UUID id = UUID.randomUUID();

        Funcionario funcionario = new Funcionario();
        funcionario.setUsuario("admin"); // login padrão
        funcionario.setSenha("admin123"); // senha padrão
        funcionario.setSetor("admin");
        funcionario.setId(id);

        salvarUsuario(funcionario);
    }

    public List<Usuario> listarClientes() {

        Optional<List<Usuario>> lista = usuarioRepository.findAllByRole(Role.ROLE_CLIENTE);
        if (lista.isPresent()) {
            return lista.get();
        } else {
            return null;
        }

    }


}
