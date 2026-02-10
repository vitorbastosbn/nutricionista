package com.vitorbastosbn.nutricionista.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Envelope genérico para todas as respostas da API.
 * Padroniza o retorno de sucesso e erro em um formato único.
 *
 * @param <T> Tipo de dado contido na resposta
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Envelope padrão de resposta da API")
public class ApiResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(
            description = "Timestamp da resposta",
            example = "2026-02-09T22:30:00"
    )
    private LocalDateTime timestamp;

    @NotNull
    @Schema(
            description = "Status HTTP da resposta",
            example = "200"
    )
    private int status;

    @Schema(
            description = "Mensagem descritiva da resposta",
            example = "Operação realizada com sucesso"
    )
    private String message;

    @Schema(description = "Dados retornados pela operação")
    private transient T data;

    @Schema(description = "Detalhes de erro (preenchido apenas em caso de erro)")
    private ErrorDetail error;

    /**
     * Cria uma resposta de sucesso.
     *
     * @param status Status HTTP
     * @param message Mensagem de sucesso
     * @param data Dados da resposta
     * @return ApiResponse configurado para sucesso
     */
    public static <T> ApiResponse<T> success(int status, String message, T data) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Cria uma resposta de sucesso com dados.
     *
     * @param message Mensagem de sucesso
     * @param data Dados da resposta
     * @return ApiResponse configurado para sucesso
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return success(200, message, data);
    }

    /**
     * Cria uma resposta de sucesso sem dados.
     *
     * @param message Mensagem de sucesso
     * @return ApiResponse configurado para sucesso
     */
    public static <T> ApiResponse<T> success(String message) {
        return success(200, message, null);
    }

    /**
     * Cria uma resposta de sucesso com status customizado.
     *
     * @param status Status HTTP customizado
     * @param message Mensagem de sucesso
     * @return ApiResponse configurado para sucesso
     */
    public static <T> ApiResponse<T> success(int status, String message) {
        return success(status, message, null);
    }

    /**
     * Cria uma resposta de erro.
     *
     * @param status Status HTTP
     * @param message Mensagem de erro
     * @param errorCode Código do erro
     * @param details Detalhes adicionais do erro
     * @return ApiResponse configurado para erro
     */
    public static <T> ApiResponse<T> error(int status, String message, String errorCode, String details) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .message(message)
                .error(ErrorDetail.builder()
                        .code(errorCode)
                        .details(details)
                        .build())
                .build();
    }

    /**
     * Cria uma resposta de erro sem detalhes adicionais.
     *
     * @param status Status HTTP
     * @param message Mensagem de erro
     * @param errorCode Código do erro
     * @return ApiResponse configurado para erro
     */
    public static <T> ApiResponse<T> error(int status, String message, String errorCode) {
        return error(status, message, errorCode, null);
    }

    /**
     * Cria uma resposta de erro simples.
     *
     * @param status Status HTTP
     * @param message Mensagem de erro
     * @return ApiResponse configurado para erro
     */
    public static <T> ApiResponse<T> error(int status, String message) {
        return error(status, message, null, null);
    }

    /**
     * Classe interna para detalhes de erro.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Detalhes do erro")
    public static class ErrorDetail implements Serializable {

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
        private java.util.List<FieldErrorDetail> fieldErrors;
    }

    /**
     * Classe interna para erros de validação de campos.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Erro de validação de um campo específico")
    public static class FieldErrorDetail implements Serializable {

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
}

