package com.vitorbastosbn.nutricionista.service;

import com.vitorbastosbn.nutricionista.domain.dto.request.RoleRequest;
import com.vitorbastosbn.nutricionista.entity.Role;
import com.vitorbastosbn.nutricionista.exception.BusinessException;
import com.vitorbastosbn.nutricionista.exception.ResourceNotFoundException;
import com.vitorbastosbn.nutricionista.mapper.RoleMapper;
import com.vitorbastosbn.nutricionista.repository.RoleRepository;
import com.vitorbastosbn.nutricionista.repository.specification.RoleSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleRequest create(RoleRequest roleDTO) {
        validateUniqueName(roleDTO.getName(), null);
        Role role = roleMapper.toRoleEntity(roleDTO);
        Role created = roleRepository.save(role);
        log.info("Role criada: {}", created.getName());
        return roleMapper.toRoleRequest(created);
    }

    @Transactional(readOnly = true)
    public Page<RoleRequest> findAll(String name, String description, String search, Pageable pageable) {
        Specification<Role> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (search != null && !search.isBlank()) {
            spec = spec.and(RoleSpecification.searchByNameOrDescription(search));
        } else {
            if (name != null && !name.isBlank()) {
                spec = spec.and(RoleSpecification.hasName(name));
            }
            if (description != null && !description.isBlank()) {
                spec = spec.and(RoleSpecification.hasDescription(description));
            }
        }

        return roleRepository.findAll(spec, pageable)
                .map(roleMapper::toRoleRequest);
    }

    @Transactional(readOnly = true)
    public RoleRequest findById(UUID id) {
        Role role = findRole(id);
        return roleMapper.toRoleRequest(role);
    }

    public RoleRequest update(UUID id, RoleRequest roleDTO) {
        Role role = findRole(id);
        if (roleDTO.getName() != null && !roleDTO.getName().equalsIgnoreCase(role.getName())) {
            validateUniqueName(roleDTO.getName(), id);
        }
        roleMapper.updateRoleFromRequest(roleDTO, role);
        Role updated = roleRepository.save(role);
        log.info("Role atualizada: {}", updated.getName());
        return roleMapper.toRoleRequest(updated);
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
}
