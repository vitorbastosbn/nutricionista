package com.vitorbastosbn.nutricionista.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "DTO para atualizar contato do usuário")
public record UpdateUserContactRequest(

        @NotNull
        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(
                regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$",
                message = "Telefone deve estar no formato (00) 00000-0000 ou (00) 0000-0000"
        )
        @Schema(
                description = "Número de telefone com DDD",
                example = "(11) 98765-4321"
        )
        String phoneNumber,

        @Pattern(
                regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$",
                message = "Telefone alternativo deve estar no formato (00) 00000-0000 ou (00) 0000-0000"
        )
        @Schema(
                description = "Número de telefone alternativo com DDD",
                example = "(11) 3456-7890"
        )
        String alternativePhone,

        @Pattern(
                regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$",
                message = "WhatsApp deve estar no formato (00) 00000-0000 ou (00) 0000-0000"
        )
        @Schema(
                description = "Número de WhatsApp com DDD",
                example = "(11) 99876-5432"
        )
        String whatsapp

) {
}

