package com.apirest.usuarios.domain.usuario;

public class CadastroNaoValidadoException extends RuntimeException {

  public CadastroNaoValidadoException(String mensagem) {
    super(mensagem);
  }
}
