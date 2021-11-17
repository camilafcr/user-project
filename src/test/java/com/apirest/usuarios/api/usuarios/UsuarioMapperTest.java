package com.apirest.usuarios.api.usuarios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.apirest.usuarios.api.usuario.UsuarioMapper;
import com.apirest.usuarios.api.usuario.UsuarioRequest;
import com.apirest.usuarios.domain.usuario.Usuario;
import com.apirest.usuarios.domain.usuario.UsuarioSituacao;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsuarioMapperTest {

  private UsuarioMapper mapper;

  @BeforeEach
  public void setUp() {
    mapper = new UsuarioMapper();
  }

  @Test
  public void deveMapearRequestParaEntity() {
    final UsuarioRequest request = UsuarioRequest.builder()
        .nome("João")
        .codigo(1)
        .cpf("34654586059")
        .email("teste@teste")
        .nascimento(LocalDate.of(1992, Month.NOVEMBER, 11))
        .senha("Usuario123")
        .situacao(UsuarioSituacao.ATIVO.name())
        .build();

    final Usuario usuario = mapper.mapRequestToEntity(request);

    assertEquals(request.getNome(), usuario.getNome());
    assertEquals(request.getCpf(), usuario.getCpf());
    assertEquals(request.getEmail(), usuario.getEmail());
    assertEquals(request.getNascimento(), usuario.getNascimento());
    assertEquals(request.getCodigo(), usuario.getCodigo());
    assertEquals(request.getSenha(), usuario.getSenha());
    assertEquals(request.getSituacao(), usuario.getSituacao().name());
  }


}
