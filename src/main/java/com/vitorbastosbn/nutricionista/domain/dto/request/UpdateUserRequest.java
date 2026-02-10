package com.vitorbastosbn.nutricionista.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "DTO para atualizar dados do usuário")
public record UpdateUserRequest(

        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 150, message = "Nome deve ter entre 3 e 150 caracteres")
        @Schema(description = "Nome completo", example = "João da Silva")
        String fullName,

        @Past(message = "Data de nascimento deve ser no passado")
        @Schema(description = "Data de nascimento", example = "1990-05-15")
        LocalDate birthDate,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email deve ser válido")
        @Schema(description = "Email", example = "joao@example.com")
        String email

) {
}

