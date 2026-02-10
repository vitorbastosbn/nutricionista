package com.vitorbastosbn.nutricionista.mapper;

import com.vitorbastosbn.nutricionista.domain.dto.request.RoleRequest;
import com.vitorbastosbn.nutricionista.entity.Role;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface RoleMapper {

    /**
     * Converte Role entity para RoleRequest DTO
     */
    RoleRequest toRoleRequest(Role role);

    /**
     * Converte RoleRequest DTO para Role entity
     * Ignora o ID pois será gerado automaticamente
     */
    @Mapping(target = "id", ignore = true)
    Role toRoleEntity(RoleRequest request);

    /**
     * Atualiza uma Role entity existente com dados do RoleRequest DTO
     * Ignora o ID para não sobrescrever
     */
    @Mapping(target = "id", ignore = true)
    void updateRoleFromRequest(RoleRequest request, @MappingTarget Role role);
}

