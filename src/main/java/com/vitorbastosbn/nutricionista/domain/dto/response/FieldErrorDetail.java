package com.vitorbastosbn.nutricionista.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Classe que representa um erro de validação de um campo específico.
 * Contém informações sobre qual campo teve erro, qual valor foi rejeitado e a mensagem do erro.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Erro de validação de um campo específico")
public class FieldErrorDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(
            description = "Nome do campo com erro",
            example = "email"
    )
    private String field;

    @Schema(
            description = "Valor rejeitado",
            example = "invalid-email"
    )
    private String rejectedValue;

    @Schema(
            description = "Mensagem de erro do campo",
            example = "Deve ser um email válido"
    )
    private String message;
}

