package com.pfc.thindesk.repository;

import com.pfc.thindesk.entity.Role;
import com.pfc.thindesk.entity.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByUsuario(String usuario);

    Optional<List<Usuario>> findAllByRole(Role role);

}
