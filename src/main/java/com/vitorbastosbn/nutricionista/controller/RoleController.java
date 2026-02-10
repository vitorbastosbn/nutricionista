package com.vitorbastosbn.nutricionista.controller;

import com.vitorbastosbn.nutricionista.controller.doc.RoleControllerAPI;
import com.vitorbastosbn.nutricionista.domain.dto.request.RoleRequest;
import com.vitorbastosbn.nutricionista.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
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
    public ResponseEntity<Page<RoleRequest>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String search) {
        log.debug("Listando todas as roles com paginação: página {}, tamanho {}", page, size);

        // Criar Pageable a partir dos parâmetros
        Sort.Order[] orders = Arrays.stream(sort)
                .map(s -> {
                    String[] parts = s.split(",");
                    String property = parts[0];
                    Sort.Direction direction = parts.length > 1 && parts[1].equalsIgnoreCase("desc")
                            ? Sort.Direction.DESC
                            : Sort.Direction.ASC;
                    return new Sort.Order(direction, property);
                })
                .toArray(Sort.Order[]::new);

        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Page<RoleRequest> roles = roleService.findAll(name, description, search, pageable);
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

