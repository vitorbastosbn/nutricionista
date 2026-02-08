package com.vitorbastosbn.nutricionista.controller;

import com.vitorbastosbn.nutricionista.controller.doc.RoleControllerAPI;
import com.vitorbastosbn.nutricionista.domain.dto.request.RoleRequest;
import com.vitorbastosbn.nutricionista.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/roles")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class RoleController implements RoleControllerAPI {

    private final RoleService roleService;

    @Override
    @PostMapping
    public ResponseEntity<RoleRequest> create(@RequestBody RoleRequest roleDTO) {
        log.info("Criando nova role: {}", roleDTO.getName());
        RoleRequest created = roleService.create(roleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<RoleRequest>> findAll() {
        log.debug("Listando todas as roles");
        List<RoleRequest> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<RoleRequest> findById(@PathVariable UUID id) {
        log.debug("Buscando role com ID: {}", id);
        RoleRequest roleDTO = roleService.findById(id);
        return ResponseEntity.ok(roleDTO);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<RoleRequest> update(@PathVariable UUID id, @RequestBody RoleRequest roleDTO) {
        log.info("Atualizando role com ID: {}", id);
        RoleRequest updated = roleService.update(id, roleDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        log.info("Deletando role com ID: {}", id);
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

