package com.apirest.usuarios.domain.usuario;

import com.apirest.usuarios.utils.ValidaCPF;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UsuarioValidacao {

  private Usuario usuario;
  private String mensagemErro;
  private List<Usuario> listaUsuarios;

  public boolean validaRegras() {
    return validaIdade() && validaCodigoDuplicado() && validaCPFDuplicado()
        && validaEmailDuplicado() && validaCPF();
  }

  private boolean validaIdade() {
    final LocalDate dataAtual = LocalDate.now();
    final Period periodo = Period.between(usuario.getNascimento(), dataAtual);

    if (periodo.getYears() >= 18) {
      return true;
    } else {
      mensagemErro = "A idade deve ser superior a 18 anos. Favor verificar.";
      return false;
    }
  }

  private boolean validaCodigoDuplicado() {
    Optional<Usuario> entidadeRepetida = listaUsuarios.stream()
        .filter(usuarioBanco -> (Objects.isNull(usuario.getUsuarioId()) ||
            !usuario.getUsuarioId().equals(usuarioBanco.getUsuarioId())) &&
            usuario.getCodigo() == usuarioBanco.getCodigo())
        .findAny();

    if (!entidadeRepetida.isPresent()) {
      return true;
    } else {
      mensagemErro = "Código já existe em outro cadastro. Favor verificar.";
      return false;
    }
  }

  private boolean validaCPFDuplicado() {
    Optional<Usuario> entidadeRepetida = listaUsuarios.stream()
        .filter(usuarioBanco -> (Objects.isNull(usuario.getUsuarioId()) ||
            !usuario.getUsuarioId().equals(usuarioBanco.getUsuarioId())) &&
            usuario.getCpf().equals(usuarioBanco.getCpf()))
        .findAny();

    if (!entidadeRepetida.isPresent()) {
      return true;
    } else {
      mensagemErro = "CPF já existe em outro cadastro. Favor verificar.";
      return false;
    }
  }

  private boolean validaEmailDuplicado() {
    Optional<Usuario> entidadeRepetida = listaUsuarios.stream()
        .filter(usuarioBanco -> (Objects.isNull(usuario.getUsuarioId()) ||
            !usuario.getUsuarioId().equals(usuarioBanco.getUsuarioId())) &&
            usuario.getEmail().equals(usuarioBanco.getEmail()) && !usuario.getEmail().equals(""))
        .findAny();

    if (!entidadeRepetida.isPresent()) {
      return true;
    } else {
      mensagemErro = "Email já existe em outro cadastro. Favor verificar.";
      return false;
    }
  }

  private boolean validaCPF() {
    if (ValidaCPF.isCPF(usuario.getCpf())) {
      return true;
    } else {
      mensagemErro = "CPF inválido. Favor verificar.";
      return false;
    }
  }
}
