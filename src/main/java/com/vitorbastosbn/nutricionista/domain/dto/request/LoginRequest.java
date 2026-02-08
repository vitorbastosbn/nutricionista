package com.vitorbastosbn.nutricionista.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Credenciais de login do usuário")
public record LoginRequest(

        @NotEmpty(message = "Email é obrigatório")
        @NotBlank(message = "Email não pode estar vazio")
        @NotNull(message = "Email não pode ser nulo")
        @Email(message = "Email deve ser válido")
        @Schema(description = "Email do usuário", example = "joao.silva@example.com")
        String email,

        @NotEmpty(message = "Senha é obrigatória")
        @NotBlank(message = "Senha não pode estar vazia")
        @NotNull(message = "Senha não pode ser nula")
        @Schema(description = "Senha do usuário", example = "senha123")
        String password
) {
}
