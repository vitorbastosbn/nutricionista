package com.vitorbastosbn.nutricionista.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vitorbastosbn.nutricionista.domain.dto.FieldError;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Resposta de erro padronizada")
public class ErrorResponse {

    @Schema(description = "Timestamp do erro", example = "2026-01-13T22:37:00")
    private LocalDateTime timestamp;

    @Schema(description = "Código de status HTTP", example = "400")
    private int status;

    @Schema(description = "Tipo de erro", example = "Bad Request")
    private String error;

    @Schema(description = "Mensagem de erro principal", example = "Falha na validação dos dados")
    private String message;

    @Schema(description = "Caminho da requisição", example = "/auth/login")
    private String path;

    @Schema(description = "Detalhes dos erros de validação")
    private List<FieldError> errors;

}

