package com.apirest.usuarios.domain.usuarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.apirest.usuarios.domain.usuario.Usuario;
import com.apirest.usuarios.domain.usuario.UsuarioSituacao;
import com.apirest.usuarios.domain.usuario.UsuarioValidacao;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsuarioValidacaoTest {

  private UsuarioValidacao validacao;

  @BeforeEach
  public void setUp() {
    validacao = new UsuarioValidacao();
  }

  @Test
  public void naoDeveValidarUsuarioComIdadeMenorIgualA18() {
    final LocalDate dataNascimento = LocalDate.of(2010, Month.NOVEMBER, 11);

    final Usuario usuario = Usuario.builder()
        .nome("João")
        .codigo(1)
        .cpf("45804163094")
        .email("teste@teste")
        .nascimento(dataNascimento)
        .build();

    validacao.setUsuario(usuario);
    validacao.setListaUsuarios(new ArrayList<>());

    assertFalse(validacao.validaRegras());
    assertEquals("A idade deve ser superior a 18 anos. Favor verificar.", validacao.getMensagemErro());
  }

  @Test
  public void naoDeveValidarUsuarioComEmailDuplicado() {
    final LocalDate dataNascimento = LocalDate.of(2000, Month.NOVEMBER, 11);

    final Usuario usuarioJaExistente = Usuario.builder()
        .nome("Maria")
        .codigo(2)
        .cpf("34654586059")
        .email("teste@teste")
        .nascimento(dataNascimento)
        .usuarioId(UUID.randomUUID())
        .build();

    final Usuario usuario = Usuario.builder()
        .nome("João")
        .codigo(1)
        .cpf("45804163094")
        .email("teste@teste")
        .nascimento(dataNascimento)
        .build();

    validacao.setUsuario(usuario);
    validacao.setListaUsuarios(Arrays.asList(usuarioJaExistente));

    assertFalse(validacao.validaRegras());
    assertEquals("Email já existe em outro cadastro. Favor verificar.", validacao.getMensagemErro());
  }

  @Test
  public void naoDeveValidarUsuarioComCPFDuplicado() {
    final LocalDate dataNascimento = LocalDate.of(2000, Month.NOVEMBER, 11);

    final Usuario usuarioJaExistente = Usuario.builder()
        .nome("Maria")
        .codigo(2)
        .cpf("34654586059")
        .email("teste@teste")
        .nascimento(dataNascimento)
        .usuarioId(UUID.randomUUID())
        .senha("Teste123")
        .situacao(UsuarioSituacao.ATIVO)
        .build();

    final Usuario usuario = Usuario.builder()
        .nome("João")
        .codigo(1)
        .cpf("34654586059")
        .email("teste@teste1")
        .nascimento(dataNascimento)
        .senha("Teste123")
        .situacao(UsuarioSituacao.ATIVO)
        .build();

    validacao.setUsuario(usuario);
    validacao.setListaUsuarios(Arrays.asList(usuarioJaExistente));

    assertFalse(validacao.validaRegras());
    assertEquals("CPF já existe em outro cadastro. Favor verificar.", validacao.getMensagemErro());
  }

  @Test
  public void naoDeveValidarUsuarioComCodigoDuplicado() {
    final LocalDate dataNascimento = LocalDate.of(2000, Month.NOVEMBER, 11);

    final Usuario usuarioJaExistente = Usuario.builder()
        .nome("Maria")
        .codigo(2)
        .cpf("45804163094")
        .email("teste@teste")
        .nascimento(dataNascimento)
        .usuarioId(UUID.randomUUID())
        .senha("Teste123")
        .situacao(UsuarioSituacao.ATIVO)
        .build();

    final Usuario usuario = Usuario.builder()
        .nome("João")
        .codigo(2)
        .cpf("34654586059")
        .email("teste@teste1")
        .nascimento(dataNascimento)
        .senha("Teste123")
        .situacao(UsuarioSituacao.ATIVO)
        .build();

    validacao.setUsuario(usuario);
    validacao.setListaUsuarios(Arrays.asList(usuarioJaExistente));

    assertFalse(validacao.validaRegras());
    assertEquals("Código já existe em outro cadastro. Favor verificar.", validacao.getMensagemErro());
  }

  @Test
  public void naoDeveValidarUsuarioComCPFInválido() {
    final LocalDate dataNascimento = LocalDate.of(2000, Month.NOVEMBER, 11);

    final Usuario usuarioJaExistente = Usuario.builder()
        .nome("Maria")
        .codigo(2)
        .cpf("34654586059")
        .email("teste@teste")
        .nascimento(dataNascimento)
        .usuarioId(UUID.randomUUID())
        .senha("Teste123")
        .situacao(UsuarioSituacao.ATIVO)
        .build();

    final Usuario usuario = Usuario.builder()
        .nome("João")
        .codigo(1)
        .cpf("111111")
        .email("teste@teste1")
        .nascimento(dataNascimento)
        .senha("Teste123")
        .situacao(UsuarioSituacao.ATIVO)
        .build();

    validacao.setUsuario(usuario);
    validacao.setListaUsuarios(Arrays.asList(usuarioJaExistente));

    assertFalse(validacao.validaRegras());
    assertEquals("CPF inválido. Favor verificar.", validacao.getMensagemErro());
  }

  @Test
  public void deveValidarUsuarioSemErro() {
    final LocalDate dataNascimento = LocalDate.of(2000, Month.NOVEMBER, 11);

    final Usuario usuarioJaExistente = Usuario.builder()
        .nome("Maria")
        .codigo(2)
        .cpf("34654586059")
        .email("teste@teste")
        .nascimento(dataNascimento)
        .usuarioId(UUID.randomUUID())
        .senha("Teste123")
        .situacao(UsuarioSituacao.ATIVO)
        .build();

    final Usuario usuario = Usuario.builder()
        .nome("João")
        .codigo(1)
        .cpf("45804163094")
        .email("teste@teste1")
        .nascimento(dataNascimento)
        .senha("Teste123")
        .situacao(UsuarioSituacao.ATIVO)
        .build();

    validacao.setUsuario(usuario);
    validacao.setListaUsuarios(Arrays.asList(usuarioJaExistente));

    assertTrue(validacao.validaRegras());
    assertNull(validacao.getMensagemErro());
  }
}
