package com.vitorbastosbn.nutricionista.controller.doc;

import com.vitorbastosbn.nutricionista.domain.dto.request.LoginRequest;
import com.vitorbastosbn.nutricionista.domain.dto.request.RegisterRequest;
import com.vitorbastosbn.nutricionista.domain.dto.request.RefreshTokenRequest;
import com.vitorbastosbn.nutricionista.domain.dto.response.AuthResponse;
import com.vitorbastosbn.nutricionista.domain.dto.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints relacionados a autenticação e autorização")
public interface AuthAPI {

    @PostMapping("/login")
    @Operation(
            summary = "Realizar login",
            description = "Autentica o usuário com email e senha, retornando Access Token e Refresh Token JWT"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login realizado com sucesso. Retorna Access Token (válido por 1 hora) e Refresh Token (válido por 7 dias)",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthResponse.class),
                            examples = @ExampleObject(
                                    name = "Login bem-sucedido",
                                    value = """
                                            {
                                              "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNzA1MTc2MDAwLCJleHAiOjE3MDUxNzk2MDB9.signature",
                                              "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0eXBlIjoicmVmcmVzaCIsInN1YiI6ImFkbWluQGV4YW1wbGUuY29tIiwiaWF0IjoxNzA1MTc2MDAwLCJleHAiOjE3MDU3ODA4MDB9.signature",
                                              "token_type": "Bearer",
                                              "expires_in": 3600,
                                              "refresh_expires_in": 604800,
                                              "user_id": "550e8400-e29b-41d4-a716-446655440000",
                                              "username": "admin@example.com",
                                              "email": "admin@example.com",
                                              "issued_at": "2026-01-13T23:30:00"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Credenciais inválidas",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Credenciais inválidas",
                                    value = """
                                            {
                                              "timestamp": "2026-01-13T23:30:00",
                                              "status": 422,
                                              "error": "Unprocessable Entity",
                                              "message": "Email ou senha inválidos",
                                              "path": "/auth/login"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida - campos obrigatórios ausentes ou inválidos",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Validação falhou",
                                    value = """
                                            {
                                              "timestamp": "2026-01-13T23:30:00",
                                              "status": 400,
                                              "error": "Bad Request",
                                              "message": "Falha na validação dos dados",
                                              "path": "/auth/login",
                                              "errors": [
                                                {
                                                  "field": "email",
                                                  "rejectedValue": "",
                                                  "message": "must not be empty"
                                                },
                                                {
                                                  "field": "password",
                                                  "rejectedValue": "",
                                                  "message": "must not be empty"
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest);

    @PostMapping("/register")
    @Operation(
            summary = "Registrar novo usuário",
            description = "Cria uma nova conta de usuário no sistema com informações completas de perfil. Retorna tokens JWT para login imediato."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário registrado com sucesso. Retorna Access Token e Refresh Token.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthResponse.class),
                            examples = @ExampleObject(
                                    name = "Registro bem-sucedido",
                                    value = """
                                            {
                                              "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2FvQGV4YW1wbGUuY29tIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTcwNTE3NjAwMCwiZXhwIjoxNzA1MTc5NjAwfQ.signature",
                                              "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0eXBlIjoicmVmcmVzaCIsInN1YiI6ImpvYW9AZXhhbXBsZS5jb20iLCJpYXQiOjE3MDUxNzYwMDAsImV4cCI6MTcwNTc4MDgwMH0.signature",
                                              "token_type": "Bearer",
                                              "expires_in": 3600,
                                              "refresh_expires_in": 604800,
                                              "user_id": "550e8400-e29b-41d4-a716-446655440001",
                                              "username": "joao@example.com",
                                              "email": "joao@example.com",
                                              "issued_at": "2026-01-13T23:30:00"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Email já cadastrado no sistema",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Email duplicado",
                                    value = """
                                            {
                                              "timestamp": "2026-01-13T23:30:00",
                                              "status": 422,
                                              "error": "Unprocessable Entity",
                                              "message": "Este email já está cadastrado no sistema",
                                              "path": "/auth/register"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos - campos obrigatórios ausentes ou formato inválido",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Validação falhou",
                                    value = """
                                            {
                                              "timestamp": "2026-01-13T23:30:00",
                                              "status": 400,
                                              "error": "Bad Request",
                                              "message": "Falha na validação dos dados",
                                              "path": "/auth/register",
                                              "errors": [
                                                {
                                                  "field": "email",
                                                  "rejectedValue": "email-invalido",
                                                  "message": "must be a well-formed email address"
                                                },
                                                {
                                                  "field": "fullName",
                                                  "rejectedValue": "",
                                                  "message": "must not be empty"
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest registerRequest);

    @PostMapping("/refresh")
    @Operation(
            summary = "Renovar tokens",
            description = "Usa o Refresh Token para obter um novo Access Token e um novo Refresh Token (Token Rotation). Ambos os tokens devem ser substituídos no cliente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tokens renovados com sucesso. IMPORTANTE: Retorna tanto um novo Access Token quanto um novo Refresh Token. O cliente deve substituir AMBOS.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthResponse.class),
                            examples = @ExampleObject(
                                    name = "Tokens renovados",
                                    value = """
                                            {
                                              "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNzA1MTc5NjAwLCJleHAiOjE3MDUxODMyMDB9.new_signature",
                                              "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0eXBlIjoicmVmcmVzaCIsInN1YiI6ImFkbWluQGV4YW1wbGUuY29tIiwiaWF0IjoxNzA1MTc5NjAwLCJleHAiOjE3MDU3ODQ0MDB9.new_signature",
                                              "token_type": "Bearer",
                                              "expires_in": 3600,
                                              "refresh_expires_in": 604800,
                                              "user_id": "550e8400-e29b-41d4-a716-446655440000",
                                              "username": "admin@example.com",
                                              "email": "admin@example.com",
                                              "issued_at": "2026-01-13T23:35:00"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Refresh Token inválido ou expirado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Token inválido",
                                    value = """
                                            {
                                              "timestamp": "2026-01-13T23:30:00",
                                              "status": 422,
                                              "error": "Unprocessable Entity",
                                              "message": "Refresh token inválido: Token expirado",
                                              "path": "/auth/refresh"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Refresh Token não informado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Token não informado",
                                    value = """
                                            {
                                              "timestamp": "2026-01-13T23:30:00",
                                              "status": 400,
                                              "error": "Bad Request",
                                              "message": "Falha na validação dos dados",
                                              "path": "/auth/refresh",
                                              "errors": [
                                                {
                                                  "field": "refreshToken",
                                                  "rejectedValue": null,
                                                  "message": "Refresh token é obrigatório"
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<AuthResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest);

}
