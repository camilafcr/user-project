package com.apirest.usuarios.api.usuarios;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.apirest.usuarios.api.usuario.UsuarioController;
import com.apirest.usuarios.api.usuario.UsuarioMapper;
import com.apirest.usuarios.domain.usuario.Usuario;
import com.apirest.usuarios.domain.usuario.UsuarioService;
import com.apirest.usuarios.domain.usuario.UsuarioSituacao;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@WebMvcTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UsuarioController usuarioController;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private UsuarioService usuarioService;

  @MockBean
  private UsuarioMapper usuarioMapper;

  @Test
  public void deveBuscarTodosUsuarios() throws Exception {
    Mockito.when(usuarioService.buscarTodos()).thenReturn(Arrays.asList(buildUsuario()));

    mockMvc.perform(MockMvcRequestBuilders.get("/v1/usuarios")).andExpect(status().isOk());
  }

  @Test
  public void deveBuscarTodosUsuariosComFiltroPorNome() throws Exception {
    Mockito.when(usuarioService.buscarTodos(any())).thenReturn(Arrays.asList(buildUsuario()));

    mockMvc.perform(
        MockMvcRequestBuilders.get("/v1/usuarios").param("nome", "João"))
        .andExpect(status().isOk());

  }

  @Test
  public void deveBuscarUsuarioEspecifico() throws Exception {
    final UUID id = UUID.randomUUID();

    Mockito.when(usuarioService.buscarUsuarioPorId(any()))
        .thenReturn(Optional.of(buildUsuario(id)));

    mockMvc.perform(MockMvcRequestBuilders.get("/v1/usuarios/{id}", id)).andExpect(status().isOk());
  }

  @Test
  public void deveRetornarStatusNotFoundQuandoUsuarioNaoForEncontrado() throws Exception {
    final UUID id = UUID.randomUUID();

    Mockito.when(usuarioService.buscarUsuarioPorId(any())).thenReturn(Optional.empty());

    mockMvc.perform(MockMvcRequestBuilders.get("/v1/usuarios/{id}", id))
        .andExpect(status().isNotFound());
  }

  @Test
  public void deveCriarUsuario() throws Exception {
    Mockito.when(usuarioService.gravar(any())).thenReturn(buildUsuario());

    mockMvc.perform(MockMvcRequestBuilders.post("/v1/usuarios")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(buildUsuario())))
        .andExpect(status().isOk());
  }

  @Test
  public void deveAtualizarUsuario() throws Exception {
    Mockito.when(usuarioService.atualizar(any(), any())).thenReturn(Optional.of(buildUsuario()));

    mockMvc.perform(MockMvcRequestBuilders.put("/v1/usuarios/{id}", UUID.randomUUID())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(buildUsuario())))
        .andExpect(status().isOk());
  }

  @Test
  public void deveDeletarUsuario() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/v1/usuarios/{id}", UUID.randomUUID()))
        .andExpect(status().isOk());
  }

  private Usuario buildUsuario() {
    return buildUsuario(null);
  }

  private Usuario buildUsuario(UUID id) {
    return Usuario.builder()
        .nome("João")
        .codigo(1)
        .cpf("34654586059")
        .email("teste@teste")
        .nascimento(LocalDate.of(1992, Month.NOVEMBER, 11))
        .usuarioId(Objects.isNull(id) ? UUID.randomUUID() : id)
        .senha("Teste123")
        .situacao(UsuarioSituacao.ATIVO)
        .build();
  }
}
