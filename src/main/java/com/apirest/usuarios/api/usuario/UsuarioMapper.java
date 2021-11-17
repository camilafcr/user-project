package com.apirest.usuarios.api.usuario;

import com.apirest.usuarios.domain.usuario.Usuario;
import com.apirest.usuarios.domain.usuario.UsuarioSituacao;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

  public Usuario mapRequestToEntity(UsuarioRequest request) {
    return Usuario.builder()
        .nome(request.getNome())
        .codigo(request.getCodigo())
        .cpf(request.getCpf())
        .nascimento((request.getNascimento()))
        .email(request.getEmail())
        .senha(request.getSenha())
        .situacao(Enum.valueOf(UsuarioSituacao.class, request.getSituacao()))
        .build();
  }
}
