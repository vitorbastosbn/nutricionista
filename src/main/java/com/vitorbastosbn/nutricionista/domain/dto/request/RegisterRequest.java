package com.vitorbastosbn.nutricionista.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "Dados para registro de novo usuário")
public record RegisterRequest(

        @NotBlank(message = "Nome completo é obrigatório")
        @Size(min = 3, max = 200, message = "Nome completo deve ter entre 3 e 200 caracteres")
        @Schema(description = "Nome completo do usuário", example = "João Silva Santos")
        String fullName,

        @NotNull(message = "Data de nascimento é obrigatória")
        @Past(message = "Data de nascimento deve ser no passado")
        @Schema(description = "Data de nascimento", example = "1990-01-15")
        LocalDate birthDate,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email deve ser válido")
        @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
        @Schema(description = "Email do usuário", example = "joao.silva@example.com")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, max = 100, message = "Senha deve ter entre 6 e 100 caracteres")
        @Schema(description = "Senha do usuário", example = "senha123")
        String password,

        @NotNull(message = "Endereço é obrigatório")
        @Valid
        @Schema(description = "Dados do endereço")
        AddressRequest address,

        @NotNull(message = "Contato é obrigatório")
        @Valid
        @Schema(description = "Dados de contato")
        ContactRequest contact
) {
}
