package com.vitorbastosbn.nutricionista.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "Request de refresh token")
public record RefreshTokenRequest(
    @NotEmpty(message = "Refresh token é obrigatório")
    @Schema(description = "Refresh token para renovar o access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    String refreshToken
) {}

