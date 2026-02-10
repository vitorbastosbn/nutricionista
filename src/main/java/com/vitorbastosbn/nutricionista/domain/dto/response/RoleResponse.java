package com.vitorbastosbn.nutricionista.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO de resposta para role")
public class RoleResponse {

    @Schema(description = "ID da role", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Nome da role", example = "ADMIN")
    private String name;

    @Schema(description = "Descrição da role", example = "Administrador do sistema")
    private String description;

}

