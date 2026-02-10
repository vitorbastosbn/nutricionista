package com.vitorbastosbn.nutricionista.controller.doc;

import com.vitorbastosbn.nutricionista.domain.dto.request.LoginRequest;
import com.vitorbastosbn.nutricionista.domain.dto.request.RegisterRequest;
import com.vitorbastosbn.nutricionista.domain.dto.request.RefreshTokenRequest;
import com.vitorbastosbn.nutricionista.domain.dto.response.AuthResponse;
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
public interface AuthControllerAPI {

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
                                    value = SwaggerSuccessExamples.AUTH_LOGIN_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Credenciais inválidas",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "Credenciais inválidas",
                                    value = SwaggerErrorExamples.INVALID_CREDENTIALS_409
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida - campos obrigatórios ausentes ou inválidos",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "Validação falhou",
                                    value = SwaggerErrorExamples.VALIDATION_ERROR_400
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
                                    value = SwaggerSuccessExamples.AUTH_REGISTER_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Email já cadastrado no sistema",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "Email duplicado",
                                    value = SwaggerErrorExamples.EMAIL_CONFLICT_409
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos - campos obrigatórios ausentes ou formato inválido",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "Validação falhou",
                                    value = SwaggerErrorExamples.VALIDATION_ERROR_400
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
                                    value = SwaggerSuccessExamples.AUTH_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Refresh Token inválido ou expirado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "Token inválido",
                                    value = SwaggerErrorExamples.INVALID_REFRESH_TOKEN_409
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Refresh Token não informado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "Token não informado",
                                    value = SwaggerErrorExamples.VALIDATION_ERROR_400
                            )
                    )
            )
    })
    ResponseEntity<AuthResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest);

}
