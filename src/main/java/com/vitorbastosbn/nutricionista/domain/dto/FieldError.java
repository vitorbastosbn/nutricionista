package com.vitorbastosbn.nutricionista.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Erro de campo individual")
public class FieldError {
    @Schema(description = "Nome do campo que falhou", example = "email")
    private String field;

    @Schema(description = "Valor rejeitado", example = "invalid-email")
    private String rejectedValue;

    @Schema(description = "Mensagem de erro", example = "deve ser um endereço de email válido")
    private String message;
}
