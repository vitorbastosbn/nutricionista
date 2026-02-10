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
@Schema(description = "DTO de resposta para endereço")
public class AddressResponse {

    @Schema(description = "ID do endereço", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Nome da rua", example = "Rua das Flores")
    private String street;

    @Schema(description = "Número do endereço", example = "123")
    private String number;

    @Schema(description = "Complemento do endereço", example = "Apto 45")
    private String complement;

    @Schema(description = "Bairro", example = "Centro")
    private String neighborhood;

    @Schema(description = "Cidade", example = "São Paulo")
    private String city;

    @Schema(description = "Estado (sigla UF)", example = "SP")
    private String state;

    @Schema(description = "CEP", example = "01234-567")
    private String zipCode;

    @Schema(description = "País", example = "Brasil")
    private String country;

}

