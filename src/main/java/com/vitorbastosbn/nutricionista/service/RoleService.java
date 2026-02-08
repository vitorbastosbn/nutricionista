package com.vitorbastosbn.nutricionista.service;

import com.vitorbastosbn.nutricionista.domain.dto.request.RoleRequest;
import com.vitorbastosbn.nutricionista.entity.Role;
import com.vitorbastosbn.nutricionista.exception.BusinessException;
import com.vitorbastosbn.nutricionista.exception.ResourceNotFoundException;
import com.vitorbastosbn.nutricionista.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleRequest create(RoleRequest roleDTO) {
        validateUniqueName(roleDTO.getName(), null);
        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());
        Role created = roleRepository.save(role);
        log.info("Role criada: {}", created.getName());
        return toDto(created);
    }

    @Transactional(readOnly = true)
    public List<RoleRequest> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoleRequest findById(UUID id) {
        Role role = findRole(id);
        return toDto(role);
    }

    public RoleRequest update(UUID id, RoleRequest roleDTO) {
        Role role = findRole(id);
        if (roleDTO.getName() != null && !roleDTO.getName().equalsIgnoreCase(role.getName())) {
            validateUniqueName(roleDTO.getName(), id);
            role.setName(roleDTO.getName());
        }
        role.setDescription(roleDTO.getDescription());
        Role updated = roleRepository.save(role);
        log.info("Role atualizada: {}", updated.getName());
        return toDto(updated);
    }

    public void delete(UUID id) {
        Role role = findRole(id);
        roleRepository.delete(role);
        log.info("Role deletada: {}", role.getName());
    }

    private Role findRole(UUID id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role não encontrada"));
    }

    private void validateUniqueName(String name, UUID currentId) {
        roleRepository.findByName(name)
                .filter(existing -> !existing.getId().equals(currentId))
                .ifPresent(existing -> {
                    throw new BusinessException("Role já existe");
                });
    }

    private RoleRequest toDto(Role role) {
        return RoleRequest.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }
}

