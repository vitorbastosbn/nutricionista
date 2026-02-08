package com.vitorbastosbn.nutricionista.repository;

import com.vitorbastosbn.nutricionista.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    /**
     * Encontra uma role pelo nome
     *
     * @param name Nome da role (ex: "ROLE_USER", "ROLE_ADMIN")
     * @return Optional contendo a role se encontrada
     */
    Optional<Role> findByName(String name);
}

