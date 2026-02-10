package com.vitorbastosbn.nutricionista.domain.dto.request;

import com.vitorbastosbn.nutricionista.entity.enums.State;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para atualizar endereço do usuário")
public record UpdateUserAddressRequest(

        @NotNull
        @NotBlank(message = "Rua é obrigatória")
        @Size(min = 3, max = 100, message = "Rua deve ter entre 3 e 100 caracteres")
        @Schema(description = "Nome da rua", example = "Rua das Flores")
        String street,

        @NotNull
        @NotBlank(message = "Número é obrigatório")
        @Size(max = 10, message = "Número deve ter no máximo 10 caracteres")
        @Schema(description = "Número do endereço", example = "123")
        String number,

        @Size(max = 60, message = "Complemento deve ter no máximo 60 caracteres")
        @Schema(description = "Complemento do endereço", example = "Apto 45")
        String complement,

        @NotNull
        @NotBlank(message = "Bairro é obrigatório")
        @Size(min = 2, max = 100, message = "Bairro deve ter entre 2 e 100 caracteres")
        @Schema(description = "Bairro", example = "Centro")
        String neighborhood,

        @NotNull
        @NotBlank(message = "Cidade é obrigatória")
        @Size(min = 2, max = 100, message = "Cidade deve ter entre 2 e 100 caracteres")
        @Schema(description = "Cidade", example = "São Paulo")
        String city,

        @NotNull(message = "Estado é obrigatório")
        @Schema(description = "Estado (sigla UF)", example = "SP")
        State state,

        @NotNull
        @NotBlank(message = "CEP é obrigatório")
        @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP deve estar no formato 00000-000 ou 00000000")
        @Size(max = 10, message = "CEP deve ter no máximo 10 caracteres")
        @Schema(description = "CEP", example = "01234-567")
        String zipCode,

        @NotNull
        @NotBlank(message = "País é obrigatório")
        @Size(min = 2, max = 60, message = "País deve ter entre 2 e 60 caracteres")
        @Schema(description = "País", example = "Brasil")
        String country

) {
}

