package com.apirest.usuarios.domain.usuario;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

  List<Usuario> findByNomeContainingIgnoreCase(String nome);
}

