package com.vitorbastosbn.nutricionista.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO de resposta para usuário")
public class UserResponse {

    @Schema(description = "ID do usuário", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Nome completo", example = "João da Silva")
    private String fullName;

    @Schema(description = "Data de nascimento", example = "1990-05-15")
    private LocalDate birthDate;

    @Schema(description = "Email", example = "joao@example.com")
    private String email;

    @Schema(description = "Dados de endereço")
    private AddressResponse address;

    @Schema(description = "Dados de contato")
    private ContactResponse contact;

    @Schema(description = "Roles atribuídos ao usuário")
    private Set<RoleResponse> roles;

}

