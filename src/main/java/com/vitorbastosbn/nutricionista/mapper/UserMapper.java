package com.vitorbastosbn.nutricionista.mapper;

import com.vitorbastosbn.nutricionista.domain.dto.request.AddressRequest;
import com.vitorbastosbn.nutricionista.domain.dto.request.ContactRequest;
import com.vitorbastosbn.nutricionista.domain.dto.request.RegisterRequest;
import com.vitorbastosbn.nutricionista.domain.dto.request.UpdateUserAddressRequest;
import com.vitorbastosbn.nutricionista.domain.dto.request.UpdateUserContactRequest;
import com.vitorbastosbn.nutricionista.domain.dto.response.UserResponse;
import com.vitorbastosbn.nutricionista.domain.dto.response.AddressResponse;
import com.vitorbastosbn.nutricionista.domain.dto.response.ContactResponse;
import com.vitorbastosbn.nutricionista.domain.dto.response.RoleResponse;
import com.vitorbastosbn.nutricionista.entity.Address;
import com.vitorbastosbn.nutricionista.entity.Contact;
import com.vitorbastosbn.nutricionista.entity.User;
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
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    Address toAddressEntity(AddressRequest request);

    @Mapping(target = "id", ignore = true)
    Contact toContactEntity(ContactRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUserEntity(RegisterRequest request);

    @Mapping(source = "roles", target = "roles")
    UserResponse toUserResponse(User user);

    AddressResponse toAddressResponse(Address address);

    ContactResponse toContactResponse(Contact contact);

    RoleResponse toRoleResponse(Role role);

    /**
     * Atualiza um Address entity existente com dados do UpdateUserAddressRequest
     * Ignora o ID para não sobrescrever
     */
    @Mapping(target = "id", ignore = true)
    void updateAddressFromRequest(UpdateUserAddressRequest request, @MappingTarget Address address);

    /**
     * Atualiza um Contact entity existente com dados do UpdateUserContactRequest
     * Ignora o ID para não sobrescrever
     */
    @Mapping(target = "id", ignore = true)
    void updateContactFromRequest(UpdateUserContactRequest request, @MappingTarget Contact contact);

    /**
     * Atualiza um User entity existente com dados do UpdateUserRequest
     * Ignora campos sensíveis/gerenciados
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "contact", ignore = true)
    void updateUserFromRequest(com.vitorbastosbn.nutricionista.domain.dto.request.UpdateUserRequest request, @MappingTarget User user);
}
