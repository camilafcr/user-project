package com.apirest.usuarios.domain.usuarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.apirest.usuarios.domain.usuario.CadastroNaoEncontradoException;
import com.apirest.usuarios.domain.usuario.CadastroNaoValidadoException;
import com.apirest.usuarios.domain.usuario.Usuario;
import com.apirest.usuarios.domain.usuario.UsuarioRepository;
import com.apirest.usuarios.domain.usuario.UsuarioService;
import com.apirest.usuarios.domain.usuario.UsuarioSituacao;
import com.apirest.usuarios.domain.usuario.UsuarioValidacao;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

  @InjectMocks
  private UsuarioService service;

  @Mock
  private UsuarioRepository repository;
  @Mock
  private UsuarioValidacao validacao;

  @Test
  public void deveGravarNovoUsuario() {
    final LocalDate dataNascimento = LocalDate.of(1992, Month.NOVEMBER, 11);

    final Usuario usuario = Usuario.builder()
        .nome("João")
        .codigo(1)
        .cpf("11111111111")
        .email("teste@teste")
        .nascimento(dataNascimento)
        .senha("Teste123")
        .situacao(UsuarioSituacao.ATIVO)
        .build();

    when(repository.save(any())).thenReturn(usuario);
    when(repository.findAll()).thenReturn(new ArrayList<>());
    when(validacao.validaRegras()).thenReturn(true);

    Usuario response = service.gravar(usuario);

    verify(repository, times(1)).save(any());
    verify(repository, times(1)).findAll();

    assertEquals(usuario.getNome(), response.getNome());
    assertEquals(usuario.getCpf(), response.getCpf());
    assertEquals(usuario.getEmail(), response.getEmail());
    assertEquals(usuario.getNascimento(), response.getNascimento());
    assertEquals(usuario.getCodigo(), response.getCodigo());
    assertEquals(usuario.getSenha(), response.getSenha());
    assertEquals(usuario.getSituacao().name(), response.getSituacao().name());
  }

  @Test
  public void deveGerarExcecaoCadastroNaoValidadoAoGravarNovoUsuario() {
    final LocalDate dataNascimento = LocalDate.of(1992, Month.NOVEMBER, 11);

    final Usuario usuario = Usuario.builder()
        .nome("João")
        .codigo(1)
        .cpf("11111111111")
        .email("teste@teste")
        .nascimento(dataNascimento)
        .senha("Teste123")
        .situacao(UsuarioSituacao.ATIVO)
        .build();

    when(repository.findAll()).thenReturn(new ArrayList<>());
    when(validacao.validaRegras()).thenReturn(false);

    assertThrows(CadastroNaoValidadoException.class, () -> service.gravar(usuario));
  }

  @Test
  public void deveAtualizarUsuario() {
    final UUID id = UUID.randomUUID();
    final LocalDate dataNascimento = LocalDate.of(1992, Month.NOVEMBER, 11);

    final Usuario usuarioGravado = Usuario.builder()
        .nome("João")
        .codigo(1)
        .cpf("11111111111")
        .email("teste@teste")
        .nascimento(dataNascimento)
        .usuarioId(id)
        .senha("Teste123")
        .situacao(UsuarioSituacao.ATIVO)
        .build();

    final Usuario usuario = Usuario.builder()
        .nome("João")
        .codigo(1)
        .cpf("34654586059")
        .email("teste@teste")
        .nascimento(dataNascimento)
        .senha("Teste123")
        .situacao(UsuarioSituacao.ATIVO)
        .build();

    when(repository.findAll()).thenReturn(Arrays.asList(usuarioGravado));
    when(repository.findById(any())).thenReturn(Optional.of(usuarioGravado));
    when(repository.save(any())).thenReturn(usuario);
    when(validacao.validaRegras()).thenReturn(true);

    Usuario response = service.atualizar(id, usuario).get();

    verify(repository, times(1)).findAll();
    verify(repository, times(1)).findById(any());
    verify(repository, times(1)).save(any());

    assertEquals(usuario.getNome(), response.getNome());
    assertEquals(usuario.getCpf(), response.getCpf());
    assertEquals(usuario.getEmail(), response.getEmail());
    assertEquals(usuario.getNascimento(), response.getNascimento());
    assertEquals(usuario.getCodigo(), response.getCodigo());
    assertEquals(usuario.getSenha(), response.getSenha());
    assertEquals(usuario.getSituacao().name(), response.getSituacao().name());
  }

  @Test
  public void deveGerarExcecaoCadastroNaoValidadoAoAtualizarUsuario() {
    final UUID id = UUID.randomUUID();
    final LocalDate dataNascimento = LocalDate.of(1992, Month.NOVEMBER, 11);

    final Usuario usuarioGravado = Usuario.builder()
        .nome("João")
        .codigo(1)
        .cpf("11111111111")
        .email("teste@teste")
        .nascimento(dataNascimento)
        .usuarioId(id)
        .senha("Teste123")
        .situacao(UsuarioSituacao.ATIVO)
        .build();

    final Usuario usuario = Usuario.builder()
        .nome("João")
        .codigo(1)
        .cpf("34654586059")
        .email("teste@teste")
        .nascimento(dataNascimento)
        .senha("Teste123")
        .situacao(UsuarioSituacao.ATIVO)
        .build();

    when(repository.findAll()).thenReturn(Arrays.asList(usuarioGravado));
    when(repository.findById(any())).thenReturn(Optional.of(usuarioGravado));
    when(validacao.validaRegras()).thenReturn(false);

    assertThrows(CadastroNaoValidadoException.class, () -> service.atualizar(id, usuario));
  }

  @Test
  public void deveGerarExcecaoCadastroNaoEncontradoAoAtualizarUsuario() {
    final UUID id = UUID.randomUUID();
    final LocalDate dataNascimento = LocalDate.of(1992, Month.NOVEMBER, 11);

    final Usuario usuario = Usuario.builder()
        .nome("João")
        .codigo(1)
        .cpf("34654586059")
        .email("teste@teste")
        .nascimento(dataNascimento)
        .usuarioId(id)
        .senha("Teste123")
        .situacao(UsuarioSituacao.ATIVO)
        .build();

    when(repository.findById(any())).thenReturn(Optional.empty());

    assertThrows(CadastroNaoEncontradoException.class, () -> service.atualizar(id, usuario));
  }

  @Test
  public void deveBuscarUsuarioPorId() {
    final UUID id = UUID.randomUUID();
    final LocalDate dataNascimento = LocalDate.of(1992, Month.NOVEMBER, 11);

    final Usuario usuario = Usuario.builder()
        .nome("João")
        .codigo(1)
        .cpf("34654586059")
        .email("teste@teste")
        .nascimento(dataNascimento)
        .usuarioId(id)
        .senha("Teste123")
        .situacao(UsuarioSituacao.ATIVO)
        .build();

    when(repository.findById(any())).thenReturn(Optional.of(usuario));

    Usuario response = service.buscarUsuarioPorId(id).get();

    verify(repository, times(1)).findById(any());

    assertEquals(usuario.getNome(), response.getNome());
    assertEquals(usuario.getCpf(), response.getCpf());
    assertEquals(usuario.getEmail(), response.getEmail());
    assertEquals(usuario.getNascimento(), response.getNascimento());
    assertEquals(usuario.getCodigo(), response.getCodigo());
    assertEquals(usuario.getSenha(), response.getSenha());
    assertEquals(usuario.getSituacao().name(), response.getSituacao().name());
  }

  @Test
  public void deveGerarExcecaoCadastroNaoEncontradoAoBuscarUsuario() {
    final UUID id = UUID.randomUUID();
    final LocalDate dataNascimento = LocalDate.of(1992, Month.NOVEMBER, 11);

    final Usuario usuario = Usuario.builder()
        .nome("João")
        .codigo(1)
        .cpf("34654586059")
        .email("teste@teste")
        .nascimento(dataNascimento)
        .usuarioId(id)
        .senha("Teste123")
        .situacao(UsuarioSituacao.ATIVO)
        .build();

    when(repository.findById(any())).thenReturn(Optional.empty());

    assertThrows(CadastroNaoEncontradoException.class, () -> service.atualizar(id, usuario));
  }

  @Test
  public void deveBuscarTodosUsuarios() {
    final LocalDate dataNascimento = LocalDate.of(1992, Month.NOVEMBER, 11);

    final Usuario usuario = Usuario.builder()
        .nome("João")
        .codigo(1)
        .cpf("34654586059")
        .email("teste@teste")
        .nascimento(dataNascimento)
        .usuarioId(UUID.randomUUID())
        .senha("Teste123")
        .situacao(UsuarioSituacao.ATIVO)
        .build();

    when(repository.findAll()).thenReturn(Arrays.asList(usuario));

    Usuario usuarioLista = service.buscarTodos().get(0);

    verify(repository, times(1)).findAll();

    assertEquals(usuario.getNome(), usuarioLista.getNome());
    assertEquals(usuario.getCpf(), usuarioLista.getCpf());
    assertEquals(usuario.getEmail(), usuarioLista.getEmail());
    assertEquals(usuario.getNascimento(), usuarioLista.getNascimento());
    assertEquals(usuario.getCodigo(), usuarioLista.getCodigo());
    assertEquals(usuario.getSenha(), usuarioLista.getSenha());
    assertEquals(usuario.getSituacao().name(), usuarioLista.getSituacao().name());
  }

  @Test
  public void deveBuscarTodosUsuariosFiltrandoPorNome() {
    final String filtroNome = "Jo";
    final LocalDate dataNascimento = LocalDate.of(1992, Month.NOVEMBER, 11);

    final Usuario usuario = Usuario.builder()
        .nome("João")
        .codigo(1)
        .cpf("34654586059")
        .email("teste@teste")
        .nascimento(dataNascimento)
        .usuarioId(UUID.randomUUID())
        .senha("Teste123")
        .situacao(UsuarioSituacao.ATIVO)
        .build();

    when(repository.findByNomeContainingIgnoreCase(any())).thenReturn(Arrays.asList(usuario));

    Usuario usuarioLista = service.buscarTodos(filtroNome).get(0);

    verify(repository, times(1)).findByNomeContainingIgnoreCase(any());

    assertEquals(usuario.getNome(), usuarioLista.getNome());
    assertEquals(usuario.getCpf(), usuarioLista.getCpf());
    assertEquals(usuario.getEmail(), usuarioLista.getEmail());
    assertEquals(usuario.getNascimento(), usuarioLista.getNascimento());
    assertEquals(usuario.getCodigo(), usuarioLista.getCodigo());
    assertEquals(usuario.getSenha(), usuarioLista.getSenha());
    assertEquals(usuario.getSituacao().name(), usuarioLista.getSituacao().name());
  }
}
