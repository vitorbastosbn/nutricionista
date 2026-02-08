package com.vitorbastosbn.nutricionista.controller.doc;

import com.vitorbastosbn.nutricionista.domain.dto.request.RoleRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Tag(name = "Roles", description = "Gerenciamento de roles")
public interface RoleControllerAPI {

    @Operation(
            summary = "Criar nova role",
            description = "Cria uma nova role no sistema"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Role criada com sucesso",
            content = @Content(schema = @Schema(implementation = RoleRequest.class))
    )
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    @ApiResponse(responseCode = "409", description = "Role já existe")
    ResponseEntity<RoleRequest> create(@Valid @RequestBody RoleRequest roleDTO);

    @Operation(
            summary = "Listar todas as roles",
            description = "Retorna uma lista de todas as roles cadastradas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de roles",
            content = @Content(schema = @Schema(implementation = RoleRequest.class))
    )
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    ResponseEntity<List<RoleRequest>> findAll();

    @Operation(
            summary = "Buscar role por ID",
            description = "Retorna os detalhes de uma role específica"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Role encontrada",
            content = @Content(schema = @Schema(implementation = RoleRequest.class))
    )
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    @ApiResponse(responseCode = "404", description = "Role não encontrada")
    ResponseEntity<RoleRequest> findById(@PathVariable UUID id);

    @Operation(
            summary = "Atualizar role",
            description = "Atualiza os dados de uma role existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Role atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = RoleRequest.class))
    )
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    @ApiResponse(responseCode = "404", description = "Role não encontrada")
    ResponseEntity<RoleRequest> update(@PathVariable UUID id, @Valid @RequestBody RoleRequest roleDTO);

    @Operation(
            summary = "Deletar role",
            description = "Remove uma role do sistema"
    )
    @ApiResponse(responseCode = "204", description = "Role deletada com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    @ApiResponse(responseCode = "404", description = "Role não encontrada")
    ResponseEntity<Void> delete(@PathVariable UUID id);

}

