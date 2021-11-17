package com.apirest.usuarios.api.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {

  @Min(1)
  @NotNull(message = "Código é obrigatório")
  private long codigo;

  @NotBlank
  @NotNull(message = "Nome é obrigatório.")
  private String nome;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @NotNull(message = "Data de nascimento é obrigatória")
  private LocalDate nascimento;

  @NotNull(message = "CPF é obrigatório")
  private String cpf;

  @NotNull(message = "Email é obrigatório")
  private String email;

  @NotNull(message = "Senha é obrigatória")
  private String senha;

  @NotNull(message = "Situação é obrigatória")
  private String situacao;
}
