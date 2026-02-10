package com.vitorbastosbn.nutricionista.controller.doc;

import com.vitorbastosbn.nutricionista.domain.dto.request.RoleRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Roles", description = "Gerenciamento de roles")
@SecurityRequirement(name = "Bearer Authentication")
public interface RoleControllerAPI {

    @Operation(
            summary = "Criar nova role",
            description = "Cria uma nova role no sistema. Requer role ADMIN"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Role criada com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RoleRequest.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de role criada",
                                    value = SwaggerSuccessExamples.ROLE_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados invalidos",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.VALIDATION_ERROR_400)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Nao autenticado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.UNAUTHORIZED_401)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - Apenas ADMIN",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.FORBIDDEN_403)
                    )
            )
    })
    ResponseEntity<RoleRequest> create(@Valid @RequestBody RoleRequest roleDTO);

    @Operation(
            summary = "Listar todas as roles com paginacao",
            description = "Retorna uma lista paginada de todas as roles cadastradas. Requer role ADMIN. Suporta ordenacao por multiplos campos. " +
                    "Permite filtrar por name, description ou usar search para buscar em ambos os campos simultaneamente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista paginada de roles obtida com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "Exemplo de resposta paginada",
                                    value = SwaggerSuccessExamples.ROLE_PAGINATED_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Nao autenticado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.UNAUTHORIZED_401)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - Apenas ADMIN",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.FORBIDDEN_403)
                    )
            )
    })
    ResponseEntity<Page<RoleRequest>> findAll(
            @Parameter(
                    description = "Numero da pagina (comecando em 0)",
                    example = "0",
                    schema = @Schema(type = "integer", defaultValue = "0")
            )
            @RequestParam(defaultValue = "0") int page,

            @Parameter(
                    description = "Tamanho da pagina (quantidade de itens por pagina)",
                    example = "10",
                    schema = @Schema(type = "integer", defaultValue = "10")
            )
            @RequestParam(defaultValue = "10") int size,

            @Parameter(
                    description = "Ordenacao no formato: propriedade(,asc|desc). Padrao e ascendente. Multiplos criterios de ordenacao sao suportados.",
                    example = "name,asc",
                    schema = @Schema(type = "string", defaultValue = "id,asc")
            )
            @RequestParam(defaultValue = "id,asc") String[] sort,

            @Parameter(
                    description = "Filtro por nome da role (busca parcial, case-insensitive)",
                    example = "ADMIN",
                    schema = @Schema(type = "string")
            )
            @RequestParam(required = false) String name,

            @Parameter(
                    description = "Filtro por descricao da role (busca parcial, case-insensitive)",
                    example = "Administrador",
                    schema = @Schema(type = "string")
            )
            @RequestParam(required = false) String description,

            @Parameter(
                    description = "Busca geral que procura em name e description simultaneamente (busca parcial, case-insensitive). Sobrescreve os filtros name e description se fornecido.",
                    example = "admin",
                    schema = @Schema(type = "string")
            )
            @RequestParam(required = false) String search
    );

    @Operation(
            summary = "Buscar role por ID",
            description = "Retorna os detalhes de uma role especifica. Requer role ADMIN"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Role encontrada com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RoleRequest.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de role",
                                    value = SwaggerSuccessExamples.ROLE_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Nao autenticado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.UNAUTHORIZED_401)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - Apenas ADMIN",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.FORBIDDEN_403)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Role nao encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.ROLE_NOT_FOUND_404)
                    )
            )
    })
    ResponseEntity<RoleRequest> findById(@PathVariable UUID id);

    @Operation(
            summary = "Atualizar role",
            description = "Atualiza os dados de uma role existente. Requer role ADMIN"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Role atualizada com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RoleRequest.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de role atualizada",
                                    value = SwaggerSuccessExamples.ROLE_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados invalidos",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.VALIDATION_ERROR_400)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Nao autenticado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.UNAUTHORIZED_401)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - Apenas ADMIN",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.FORBIDDEN_403)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Role nao encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.ROLE_NOT_FOUND_404)
                    )
            )
    })
    ResponseEntity<RoleRequest> update(@PathVariable UUID id, @Valid @RequestBody RoleRequest roleDTO);

    @Operation(
            summary = "Deletar role",
            description = "Remove uma role do sistema. Requer role ADMIN"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Role deletada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Nao autenticado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.UNAUTHORIZED_401)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado - Apenas ADMIN",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.FORBIDDEN_403)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Role nao encontrada",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.ROLE_NOT_FOUND_404)
                    )
            )
    })
    ResponseEntity<Void> delete(@PathVariable UUID id);

}

