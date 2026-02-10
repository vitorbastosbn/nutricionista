package com.vitorbastosbn.nutricionista.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Classe que representa os detalhes de erro em uma resposta da API.
 * Contém o código do erro, detalhes adicionais e erros de validação por campo.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Detalhes do erro")
public class ErrorDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(
            description = "Código do erro para referência",
            example = "VALIDATION_ERROR"
    )
    private String code;

    @Schema(
            description = "Detalhes adicionais do erro",
            example = "Campo 'email' é obrigatório"
    )
    private String details;

    @Schema(description = "Erros de validação por campo")
    private List<FieldErrorDetail> fieldErrors;
}

