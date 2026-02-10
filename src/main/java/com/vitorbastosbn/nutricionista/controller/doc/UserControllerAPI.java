package com.vitorbastosbn.nutricionista.controller.doc;

import com.vitorbastosbn.nutricionista.domain.dto.request.UpdateUserRequest;
import com.vitorbastosbn.nutricionista.domain.dto.request.UpdateUserAddressRequest;
import com.vitorbastosbn.nutricionista.domain.dto.request.UpdateUserContactRequest;
import com.vitorbastosbn.nutricionista.domain.dto.response.UserResponse;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.Page;

@RequestMapping("/users")
@Tag(name = "Gerenciamento de Usuários", description = "Endpoints para gerenciamento de dados de usuários, endereços, contatos e roles")
@SecurityRequirement(name = "Bearer Authentication")
public interface UserControllerAPI {

    @GetMapping("/me")
    @Operation(
            summary = "Obter dados do usuário autenticado",
            description = "Retorna todas as informações do usuário atualmente autenticado, incluindo endereço, contato e roles"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário obtido com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de usuário",
                                    value = SwaggerSuccessExamples.USER_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado - Token ausente ou inválido",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "Não autenticado",
                                    value = SwaggerErrorExamples.UNAUTHORIZED_401
                            )
                    )
            )
    })
    ResponseEntity<UserResponse> getCurrentUser();

    @GetMapping("/{userId}")
    @Operation(
            summary = "Obter dados de um usuário específico",
            description = "Retorna todas as informações de um usuário pelo ID. Apenas ADMIN pode acessar dados de outros usuários"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário obtido com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de usuário",
                                    value = SwaggerSuccessExamples.USER_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.UNAUTHORIZED_401)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Sem permissão - Apenas ADMIN pode acessar dados de outros usuários",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.FORBIDDEN_403)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.USER_NOT_FOUND_404)
                    )
            )
    })
    ResponseEntity<UserResponse> getUserById(
            @PathVariable
            @Parameter(description = "ID do usuário", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", required = true)
            UUID userId);

    @GetMapping
    @Operation(
            summary = "Listar todos os usuários com paginação",
            description = "Retorna uma lista paginada de todos os usuários cadastrados no sistema. Requer role ADMIN. " +
                    "Suporta ordenação por múltiplos campos. Permite filtrar por fullName, email ou usar search para buscar em ambos os campos simultaneamente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista paginada de usuários obtida com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "Exemplo de resposta paginada",
                                    value = SwaggerSuccessExamples.USER_PAGINATED_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.UNAUTHORIZED_401)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Sem permissão - Apenas ADMIN pode listar usuários",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.FORBIDDEN_403)
                    )
            )
    })
    ResponseEntity<Page<UserResponse>> getAllUsers(
            @Parameter(
                    description = "Número da página (começando em 0)",
                    example = "0",
                    schema = @Schema(type = "integer", defaultValue = "0")
            )
            @RequestParam(defaultValue = "0") int page,

            @Parameter(
                    description = "Tamanho da página (quantidade de itens por página)",
                    example = "10",
                    schema = @Schema(type = "integer", defaultValue = "10")
            )
            @RequestParam(defaultValue = "10") int size,

            @Parameter(
                    description = "Ordenação no formato: propriedade(,asc|desc). " +
                            "Padrão é ascendente. Múltiplos critérios de ordenação são suportados.",
                    example = "fullName,asc",
                    schema = @Schema(type = "string", defaultValue = "id,asc")
            )
            @RequestParam(defaultValue = "id,asc") String[] sort,

            @Parameter(
                    description = "Filtro por nome completo do usuário (busca parcial, case-insensitive)",
                    example = "João Silva",
                    schema = @Schema(type = "string")
            )
            @RequestParam(required = false) String fullName,

            @Parameter(
                    description = "Filtro por email do usuário (busca parcial, case-insensitive)",
                    example = "joao@example.com",
                    schema = @Schema(type = "string")
            )
            @RequestParam(required = false) String email,

            @Parameter(
                    description = "Busca geral que procura em fullName e email simultaneamente (busca parcial, case-insensitive). Sobrescreve os filtros fullName e email se fornecido.",
                    example = "joao",
                    schema = @Schema(type = "string")
            )
            @RequestParam(required = false) String search
    );

    @PutMapping("/me")
    @Operation(
            summary = "Atualizar dados do usuário autenticado",
            description = "Atualiza nome completo, data de nascimento e email do usuário autenticado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário atualizado com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos - Validação falhou",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.VALIDATION_ERROR_400)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.UNAUTHORIZED_401)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email já cadastrado no sistema",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.EMAIL_CONFLICT_409)
                    )
            )
    })
    ResponseEntity<UserResponse> updateCurrentUser(
            @RequestBody @Valid UpdateUserRequest request);

    @PutMapping("/{userId}")
    @Operation(
            summary = "Atualizar dados de um usuário específico",
            description = "Atualiza nome completo, data de nascimento e email de um usuário. Requer role ADMIN"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário atualizado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.VALIDATION_ERROR_400)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.UNAUTHORIZED_401)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Sem permissão - Apenas ADMIN",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.FORBIDDEN_403)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.USER_NOT_FOUND_404)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email já cadastrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.EMAIL_CONFLICT_409)
                    )
            )
    })
    ResponseEntity<UserResponse> updateUser(
            @PathVariable
            @Parameter(description = "ID do usuário", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", required = true)
            UUID userId,
            @RequestBody @Valid UpdateUserRequest request);

    @PutMapping("/me/address")
    @Operation(
            summary = "Atualizar endereço do usuário autenticado",
            description = "Atualiza todos os dados de endereço do usuário autenticado. Se não existir, será criado automaticamente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Endereço atualizado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.VALIDATION_ERROR_400)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.UNAUTHORIZED_401)
                    )
            )
    })
    ResponseEntity<UserResponse> updateCurrentUserAddress(
            @RequestBody @Valid UpdateUserAddressRequest request);

    @PutMapping("/{userId}/address")
    @Operation(
            summary = "Atualizar endereço de um usuário específico",
            description = "Atualiza todos os dados de endereço de um usuário. Requer role ADMIN"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Endereço atualizado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.VALIDATION_ERROR_400)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.UNAUTHORIZED_401)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Sem permissão - Apenas ADMIN",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.FORBIDDEN_403)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.USER_NOT_FOUND_404)
                    )
            )
    })
    ResponseEntity<UserResponse> updateUserAddress(
            @PathVariable
            @Parameter(description = "ID do usuário", required = true)
            UUID userId,
            @RequestBody @Valid UpdateUserAddressRequest request);

    @PutMapping("/me/contact")
    @Operation(
            summary = "Atualizar contato do usuário autenticado",
            description = "Atualiza telefone, telefone alternativo e WhatsApp do usuário autenticado. Se não existir, será criado automaticamente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Contato atualizado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.VALIDATION_ERROR_400)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.UNAUTHORIZED_401)
                    )
            )
    })
    ResponseEntity<UserResponse> updateCurrentUserContact(
            @RequestBody @Valid UpdateUserContactRequest request);

    @PutMapping("/{userId}/contact")
    @Operation(
            summary = "Atualizar contato de um usuário específico",
            description = "Atualiza telefone, telefone alternativo e WhatsApp de um usuário. Requer role ADMIN"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Contato atualizado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.VALIDATION_ERROR_400)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.UNAUTHORIZED_401)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Sem permissão - Apenas ADMIN",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.FORBIDDEN_403)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.USER_NOT_FOUND_404)
                    )
            )
    })
    ResponseEntity<UserResponse> updateUserContact(
            @PathVariable
            @Parameter(description = "ID do usuário", required = true)
            UUID userId,
            @RequestBody @Valid UpdateUserContactRequest request);

    @PutMapping("/{userId}/roles/{roleId}")
    @Operation(
            summary = "Adicionar role a um usuário",
            description = "Adiciona uma role a um usuário específico. Requer role ADMIN"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Role adicionada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.UNAUTHORIZED_401)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Sem permissão - Apenas ADMIN",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.FORBIDDEN_403)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário ou role não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.USER_NOT_FOUND_404)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Usuário já possui essa role",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.ROLE_CONFLICT_409)
                    )
            )
    })
    ResponseEntity<UserResponse> addRoleToUser(
            @PathVariable
            @Parameter(description = "ID do usuário", required = true)
            UUID userId,
            @PathVariable
            @Parameter(description = "ID da role", required = true)
            UUID roleId);

    @DeleteMapping("/{userId}/roles/{roleId}")
    @Operation(
            summary = "Remover role de um usuário",
            description = "Remove uma role de um usuário específico. Requer role ADMIN. Um usuário deve ter pelo menos uma role"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Role removida com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.UNAUTHORIZED_401)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Sem permissão - Apenas ADMIN",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.FORBIDDEN_403)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário ou role não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.USER_NOT_FOUND_404)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Usuário não possui essa role ou tentativa de remover última role",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.LAST_ROLE_CONFLICT_409)
                    )
            )
    })
    ResponseEntity<UserResponse> removeRoleFromUser(
            @PathVariable
            @Parameter(description = "ID do usuário", required = true)
            UUID userId,
            @PathVariable
            @Parameter(description = "ID da role", required = true)
            UUID roleId);

    @PutMapping("/{userId}/roles")
    @Operation(
            summary = "Atualizar todas as roles de um usuário",
            description = "Atualiza o conjunto completo de roles de um usuário. Requer role ADMIN. Um usuário deve ter pelo menos uma role"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Roles atualizadas com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Lista de roles vazia",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.VALIDATION_ERROR_400)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.UNAUTHORIZED_401)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Sem permissão - Apenas ADMIN",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.FORBIDDEN_403)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário ou alguma role não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.USER_NOT_FOUND_404)
                    )
            )
    })
    ResponseEntity<UserResponse> updateUserRoles(
            @PathVariable
            @Parameter(description = "ID do usuário", required = true)
            UUID userId,
            @RequestBody
            @Parameter(description = "Lista de IDs de roles", required = true)
            Set<UUID> roleIds);

    @DeleteMapping("/me")
    @Operation(
            summary = "Deletar conta do usuário autenticado",
            description = "Deleta a conta do usuário autenticado. A operação é irreversível"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuário deletado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.UNAUTHORIZED_401)
                    )
            )
    })
    ResponseEntity<Void> deleteCurrentUser();

    @DeleteMapping("/{userId}")
    @Operation(
            summary = "Deletar um usuário específico",
            description = "Deleta um usuário específico pelo ID. Requer role ADMIN. A operação é irreversível"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuário deletado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autenticado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.UNAUTHORIZED_401)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Sem permissão - Apenas ADMIN",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.FORBIDDEN_403)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = SwaggerErrorExamples.USER_NOT_FOUND_404)
                    )
            )
    })
    ResponseEntity<Void> deleteUser(
            @PathVariable
            @Parameter(description = "ID do usuário", required = true)
            UUID userId);

}


