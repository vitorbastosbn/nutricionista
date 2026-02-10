package com.vitorbastosbn.nutricionista.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO de resposta para contato")
public class ContactResponse {

    @Schema(description = "ID do contato", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Nome do contato de emergência", example = "Maria Silva")
    private String emergencyContact;

    @Schema(description = "Telefone do contato de emergência com DDD", example = "(11) 98765-4321")
    private String emergencyPhone;

    @Schema(description = "Número de telefone principal com DDD", example = "(11) 98765-4321")
    private String phoneNumber;

    @Schema(description = "Número de telefone alternativo com DDD", example = "(11) 3456-7890")
    private String alternativePhone;

    @Schema(description = "Indica se possui WhatsApp no telefone principal", example = "true")
    private boolean whatsapp;
}

