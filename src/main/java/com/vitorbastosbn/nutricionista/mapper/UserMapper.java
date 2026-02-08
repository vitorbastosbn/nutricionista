package com.vitorbastosbn.nutricionista.mapper;

import com.vitorbastosbn.nutricionista.domain.dto.request.AddressRequest;
import com.vitorbastosbn.nutricionista.domain.dto.request.ContactRequest;
import com.vitorbastosbn.nutricionista.domain.dto.request.RegisterRequest;
import com.vitorbastosbn.nutricionista.entity.Address;
import com.vitorbastosbn.nutricionista.entity.Contact;
import com.vitorbastosbn.nutricionista.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserMapper {

    Address toAddressEntity(AddressRequest request);

    Contact toContactEntity(ContactRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUserEntity(RegisterRequest request);
}

