package com.vitorbastosbn.nutricionista.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados de contato")
public record ContactRequest(

        @NotNull
        @NotBlank(message = "Nome do contato de emergência é obrigatório")
        @Size(min = 3, max = 150, message = "Nome do contato de emergência deve ter entre 3 e 150 caracteres")
        @Schema(
                description = "Nome completo do contato de emergência",
                example = "Maria Silva"
        )
        String emergencyContact,

        @NotNull
        @NotBlank(message = "Telefone de emergência é obrigatório")
        @Pattern(
                regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$",
                message = "Telefone de emergência deve estar no formato (00) 00000-0000 ou (00) 0000-0000"
        )
        @Schema(
                description = "Número do telefone de emergência com DDD",
                example = "(11) 98765-4321"
        )
        String emergencyPhone,

        @NotNull
        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(
                regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$",
                message = "Telefone deve estar no formato (00) 00000-0000 ou (00) 0000-0000"
        )
        @Schema(
                description = "Número de telefone principal com DDD",
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

        @NotNull(message = "É obrigatório informar se possui WhatsApp")
        @Schema(
                description = "Indica se possui WhatsApp no telefone principal",
                example = "true"
        )
        Boolean whatsapp
) {
}
