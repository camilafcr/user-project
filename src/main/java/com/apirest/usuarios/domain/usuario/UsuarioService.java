package com.apirest.usuarios.domain.usuario;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository repository;

  @Autowired
  private UsuarioValidacao validacao;

  public Usuario gravar(Usuario usuario) {
    validacao.setListaUsuarios(buscarTodos());
    validacao.setUsuario(usuario);

    if (validacao.validaRegras()) {
      return repository.save(usuario);
    } else {
      throw new CadastroNaoValidadoException(validacao.getMensagemErro());
    }
  }

  public Optional<Usuario> atualizar(UUID id, Usuario usuario) {
    Optional<Usuario> usuarioBancoOpt = repository.findById(id);

    if (usuarioBancoOpt.isPresent()) {
      Usuario usuarioBanco = usuarioBancoOpt.get();
      usuario.setUsuarioId(usuarioBanco.getUsuarioId());

      validacao.setListaUsuarios(buscarTodos());
      validacao.setUsuario(usuario);

      if (validacao.validaRegras()) {
        return Optional.of(repository.save(atualizaEntity(usuarioBanco, usuario)));
      } else {
        throw new CadastroNaoValidadoException(validacao.getMensagemErro());
      }
    } else {
      throw new CadastroNaoEncontradoException(id);
    }

  }

  public List<Usuario> buscarTodos() {
    return buscarTodos(null);
  }

  public List<Usuario> buscarTodos(String nome) {
    if (Objects.isNull(nome)) {
      return repository.findAll();
    } else {
      return repository.findByNomeContainingIgnoreCase(nome);
    }

  }

  public Optional<Usuario> buscarUsuarioPorId(UUID id) {
    return repository.findById(id);
  }

  public void deletaUsuario(UUID id) {
    repository.deleteById(id);
  }

  private Usuario atualizaEntity(Usuario usuarioBanco, Usuario usuarioRequisicao) {
    usuarioBanco.setCodigo(usuarioRequisicao.getCodigo());
    usuarioBanco.setCpf(usuarioRequisicao.getCpf());
    usuarioBanco.setEmail(usuarioRequisicao.getEmail());
    usuarioBanco.setNascimento(usuarioRequisicao.getNascimento());
    usuarioBanco.setNome(usuarioRequisicao.getNome());
    usuarioBanco.setSenha(usuarioRequisicao.getSenha());
    usuarioBanco.setSituacao(usuarioRequisicao.getSituacao());

    return usuarioBanco;
  }
}
