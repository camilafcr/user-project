package com.apirest.usuarios.domain.usuario;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CadastroNaoEncontradoException extends RuntimeException {

  public CadastroNaoEncontradoException(UUID id) {
    super("Cadastro não encontrado. ID: " + id);
  }
}
