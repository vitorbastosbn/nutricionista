package com.vitorbastosbn.nutricionista.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta de autenticação com tokens")
public class AuthResponse {

    @JsonProperty("access_token")
    @Schema(description = "Token de acesso JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @JsonProperty("refresh_token")
    @Schema(description = "Token de atualização", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refreshToken;

    @JsonProperty("token_type")
    @Schema(description = "Tipo de token", example = "Bearer")
    private String tokenType;

    @JsonProperty("expires_in")
    @Schema(description = "Tempo de expiração do access token em segundos", example = "3600")
    private Long expiresIn;

    @JsonProperty("refresh_expires_in")
    @Schema(description = "Tempo de expiração do refresh token em segundos", example = "604800")
    private Long refreshExpiresIn;

    @JsonProperty("user_id")
    @Schema(description = "ID do usuário autenticado", example = "123e4567-e89b-12d3-a456-426614174000")
    private String userId;

    @JsonProperty("username")
    @Schema(description = "Username do usuário", example = "joao.silva")
    private String username;

    @JsonProperty("email")
    @Schema(description = "Email do usuário", example = "joao@example.com")
    private String email;

    @JsonProperty("issued_at")
    @Schema(description = "Data e hora de emissão do token", example = "2026-01-13T22:43:00")
    private LocalDateTime issuedAt;
}

