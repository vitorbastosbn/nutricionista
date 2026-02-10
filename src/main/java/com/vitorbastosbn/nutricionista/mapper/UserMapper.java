package com.vitorbastosbn.nutricionista.mapper;

import com.vitorbastosbn.nutricionista.domain.dto.request.AddressRequest;
import com.vitorbastosbn.nutricionista.domain.dto.request.ContactRequest;
import com.vitorbastosbn.nutricionista.domain.dto.request.RegisterRequest;
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
}

