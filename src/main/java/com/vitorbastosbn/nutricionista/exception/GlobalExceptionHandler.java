package com.vitorbastosbn.nutricionista.exception;

import com.vitorbastosbn.nutricionista.domain.dto.response.ApiResponse;
import com.vitorbastosbn.nutricionista.domain.dto.response.ErrorDetail;
import com.vitorbastosbn.nutricionista.domain.dto.response.FieldErrorDetail;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<FieldErrorDetail> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> FieldErrorDetail.builder()
                        .field(error.getField())
                        .rejectedValue(error.getRejectedValue() != null ? error.getRejectedValue().toString() : null)
                        .message(error.getDefaultMessage())
                        .build())
                .toList();

        ErrorDetail errorDetail = ErrorDetail.builder()
                .code("VALIDATION_ERROR")
                .details("Falha na validação dos dados. Verifique os campos com erro abaixo.")
                .fieldErrors(fieldErrors)
                .build();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .timestamp(java.time.LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Dados inválidos foram enviados")
                .error(errorDetail)
                .build();

        log.warn("Erro de validação em {}: {} erros encontrados", request.getRequestURI(), fieldErrors.size());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ErrorDetail errorDetail = ErrorDetail.builder()
                .code("RESOURCE_NOT_FOUND")
                .details(ex.getMessage())
                .build();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .timestamp(java.time.LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message("Recurso não encontrado")
                .error(errorDetail)
                .build();

        log.warn("Recurso não encontrado em {}: {}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(
            BusinessException ex,
            HttpServletRequest request) {

        ErrorDetail errorDetail = ErrorDetail.builder()
                .code("BUSINESS_ERROR")
                .details(ex.getMessage())
                .build();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .timestamp(java.time.LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .message("Violação de regra de negócio")
                .error(errorDetail)
                .build();

        log.warn("Erro de negócio em {}: {}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(
            AccessDeniedException ex,
            HttpServletRequest request) {

        ErrorDetail errorDetail = ErrorDetail.builder()
                .code("ACCESS_DENIED")
                .details("Você não tem permissão para acessar este recurso")
                .build();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .timestamp(java.time.LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .message("Acesso negado")
                .error(errorDetail)
                .build();

        log.warn("Acesso negado em {}", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoHandlerFound(
            NoHandlerFoundException ex,
            HttpServletRequest request) {

        ErrorDetail errorDetail = ErrorDetail.builder()
                .code("ENDPOINT_NOT_FOUND")
                .details(String.format("Endpoint %s %s não encontrado", ex.getHttpMethod(), ex.getRequestURL()))
                .build();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .timestamp(java.time.LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message("Endpoint não encontrado")
                .error(errorDetail)
                .build();

        log.warn("Endpoint não encontrado: {} {}", ex.getHttpMethod(), ex.getRequestURL());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        ErrorDetail errorDetail = ErrorDetail.builder()
                .code("INTERNAL_SERVER_ERROR")
                .details("Um erro inesperado ocorreu. Por favor, tente novamente mais tarde.")
                .build();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .timestamp(java.time.LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Erro interno do servidor")
                .error(errorDetail)
                .build();

        log.error("Erro não tratado em {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

