package com.apirest.usuarios.api.usuario;

import com.apirest.usuarios.domain.usuario.CadastroNaoEncontradoException;
import com.apirest.usuarios.domain.usuario.CadastroNaoValidadoException;
import com.apirest.usuarios.domain.usuario.Usuario;
import com.apirest.usuarios.domain.usuario.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/usuarios")
@Api(value="API REST Usuários")
@CrossOrigin(origins="*")
public class UsuarioController {

  @Autowired
  private UsuarioService service;

  @Autowired
  private UsuarioMapper mapper;

  @ApiOperation(value="Retorna uma lista de Usuários. Pode ser filtrado por nome, incluindo o parâmetro 'nome' na requisição.")
  @GetMapping
  public List<Usuario> buscaTodos(@RequestParam (required = false) String nome) {
    return service.buscarTodos(nome);
  }

  @ApiOperation(value="Retorna um usuário específico.")
  @GetMapping("/{id}")
  public Usuario buscarUsuarioPorId(@PathVariable("id") UUID id)
      throws CadastroNaoEncontradoException {
    return service.buscarUsuarioPorId(id)
        .orElseThrow(() -> new CadastroNaoEncontradoException(id));
  }

  @ApiOperation(value="Inclui um novo usuário.")
  @PostMapping
  public Usuario novoUsuario(@RequestBody @Valid UsuarioRequest request)  {
    return service.gravar(mapper.mapRequestToEntity(request));
  }

  @ApiOperation(value="Atualiza um usuário. Deve ser passado o id desejado por parâmetro.")
  @PutMapping("/{id}")
  public Usuario atualizaUsuario(@PathVariable("id") UUID id,
      @RequestBody @Valid UsuarioRequest request) {
    return service.atualizar(id, mapper.mapRequestToEntity(request))
        .orElseThrow(() -> new CadastroNaoEncontradoException(id));
  }

  @ApiOperation(value="Exclui um usuário a partir de um id informado.")
  @DeleteMapping("/{id}")
  public void deletaUsuario(@PathVariable("id") UUID id) {
    service.deletaUsuario(id);
  }

  @ExceptionHandler
  public ResponseEntity<String> tratamentoCadastroNaoValidadoException(
      CadastroNaoValidadoException exception
  ) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(exception.getMessage());
  }

  @ExceptionHandler
  public ResponseEntity<String> tratamentoCadastroNaoEncontradoException(
      CadastroNaoEncontradoException exception
  ) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(exception.getMessage());
  }
}
