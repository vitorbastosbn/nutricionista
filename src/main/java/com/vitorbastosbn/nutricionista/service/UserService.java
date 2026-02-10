package com.vitorbastosbn.nutricionista.service;

import com.vitorbastosbn.nutricionista.domain.dto.request.UpdateUserRequest;
import com.vitorbastosbn.nutricionista.domain.dto.request.UpdateUserAddressRequest;
import com.vitorbastosbn.nutricionista.domain.dto.request.UpdateUserContactRequest;
import com.vitorbastosbn.nutricionista.domain.dto.response.UserResponse;
import com.vitorbastosbn.nutricionista.entity.User;
import com.vitorbastosbn.nutricionista.entity.Address;
import com.vitorbastosbn.nutricionista.entity.Contact;
import com.vitorbastosbn.nutricionista.entity.Role;
import com.vitorbastosbn.nutricionista.exception.BusinessException;
import com.vitorbastosbn.nutricionista.exception.ResourceNotFoundException;
import com.vitorbastosbn.nutricionista.repository.UserRepository;
import com.vitorbastosbn.nutricionista.repository.RoleRepository;
import com.vitorbastosbn.nutricionista.repository.specification.UserSpecification;
import com.vitorbastosbn.nutricionista.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser() {
        User user = getCurrentAuthenticatedUser();
        log.info("Obtendo dados do usuário atual: {}", user.getEmail());
        return userMapper.toUserResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(UUID userId) {
        User user = findUserById(userId);
        log.info("Obtendo dados do usuário: {}", user.getEmail());
        return userMapper.toUserResponse(user);
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(String fullName, String email, String search, Pageable pageable) {
        log.info("Listando usuários com paginação: página {}, tamanho {}", pageable.getPageNumber(), pageable.getPageSize());

        Specification<User> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (search != null && !search.isBlank()) {
            spec = spec.and(UserSpecification.searchByFullNameOrEmail(search));
        } else {
            if (fullName != null && !fullName.isBlank()) {
                spec = spec.and(UserSpecification.hasFullName(fullName));
            }
            if (email != null && !email.isBlank()) {
                spec = spec.and(UserSpecification.hasEmail(email));
            }
        }

        return userRepository.findAll(spec, pageable)
                .map(userMapper::toUserResponse);
    }

    public UserResponse updateCurrentUser(UpdateUserRequest request) {
        User user = getCurrentAuthenticatedUser();
        return updateUserData(user, request);
    }

    public UserResponse updateUser(UUID userId, UpdateUserRequest request) {
        User user = findUserById(userId);
        return updateUserData(user, request);
    }

    private UserResponse updateUserData(User user, UpdateUserRequest request) {
        // Valida se o novo email já está em uso por outro usuário
        if (!user.getEmail().equals(request.email()) && userRepository.existsByEmail(request.email())) {
            log.warn("Tentativa de atualizar email para um já existente: {}", request.email());
            throw new BusinessException("Este email já está cadastrado no sistema");
        }

        user.setFullName(request.fullName());
        user.setBirthDate(request.birthDate());
        user.setEmail(request.email());

        User updated = userRepository.save(user);
        log.info("Usuário atualizado: {}", updated.getEmail());
        return userMapper.toUserResponse(updated);
    }

    public UserResponse updateCurrentUserAddress(UpdateUserAddressRequest request) {
        User user = getCurrentAuthenticatedUser();
        return updateUserAddress(user, request);
    }

    public UserResponse updateUserAddress(UUID userId, UpdateUserAddressRequest request) {
        User user = findUserById(userId);
        return updateUserAddress(user, request);
    }

    private UserResponse updateUserAddress(User user, UpdateUserAddressRequest request) {
        Address address = user.getAddress();

        if (address == null) {
            address = new Address();
            user.setAddress(address);
        }

        address.setStreet(request.street());
        address.setNumber(request.number());
        address.setComplement(request.complement());
        address.setNeighborhood(request.neighborhood());
        address.setCity(request.city());
        address.setState(request.state());
        address.setZipCode(request.zipCode());
        address.setCountry(request.country());

        User updated = userRepository.save(user);
        log.info("Endereço do usuário {} atualizado", updated.getEmail());
        return userMapper.toUserResponse(updated);
    }

    public UserResponse updateCurrentUserContact(UpdateUserContactRequest request) {
        User user = getCurrentAuthenticatedUser();
        return updateUserContact(user, request);
    }

    public UserResponse updateUserContact(UUID userId, UpdateUserContactRequest request) {
        User user = findUserById(userId);
        return updateUserContact(user, request);
    }

    private UserResponse updateUserContact(User user, UpdateUserContactRequest request) {
        Contact contact = user.getContact();

        if (contact == null) {
            contact = new Contact();
            user.setContact(contact);
        }

        contact.setEmergencyContact(request.emergencyContact());
        contact.setEmergencyPhone(request.emergencyPhone());
        contact.setPhoneNumber(request.phoneNumber());
        contact.setAlternativePhone(request.alternativePhone());
        contact.setWhatsapp(request.whatsapp());

        User updated = userRepository.save(user);
        log.info("Contato do usuário {} atualizado", updated.getEmail());
        return userMapper.toUserResponse(updated);
    }

    public UserResponse addRoleToUser(UUID userId, UUID roleId) {
        User user = findUserById(userId);
        Role role = findRoleById(roleId);

        if (user.getRoles().contains(role)) {
            log.warn("Usuário {} já possui a role {}", user.getEmail(), role.getName());
            throw new BusinessException("Este usuário já possui essa role");
        }

        user.getRoles().add(role);
        User updated = userRepository.save(user);
        log.info("Role {} adicionada ao usuário {}", role.getName(), user.getEmail());
        return userMapper.toUserResponse(updated);
    }

    public UserResponse removeRoleFromUser(UUID userId, UUID roleId) {
        User user = findUserById(userId);
        Role role = findRoleById(roleId);

        if (!user.getRoles().contains(role)) {
            log.warn("Usuário {} não possui a role {}", user.getEmail(), role.getName());
            throw new BusinessException("Este usuário não possui essa role");
        }

        // Garante que o usuário sempre tenha pelo menos uma role
        if (user.getRoles().size() <= 1) {
            log.warn("Tentativa de remover a única role do usuário {}", user.getEmail());
            throw new BusinessException("Um usuário deve ter pelo menos uma role");
        }

        user.getRoles().remove(role);
        User updated = userRepository.save(user);
        log.info("Role {} removida do usuário {}", role.getName(), user.getEmail());
        return userMapper.toUserResponse(updated);
    }

    public UserResponse updateUserRoles(UUID userId, Set<UUID> roleIds) {
        User user = findUserById(userId);

        if (roleIds == null || roleIds.isEmpty()) {
            log.warn("Tentativa de atualizar roles com lista vazia para usuário {}", user.getEmail());
            throw new BusinessException("Um usuário deve ter pelo menos uma role");
        }

        Set<Role> roles = new HashSet<>();
        for (UUID roleId : roleIds) {
            roles.add(findRoleById(roleId));
        }

        user.setRoles(roles);
        User updated = userRepository.save(user);
        log.info("Roles do usuário {} atualizadas", updated.getEmail());
        return userMapper.toUserResponse(updated);
    }

    public void deleteCurrentUser() {
        User user = getCurrentAuthenticatedUser();
        deleteUser(user.getId());
    }

    public void deleteUser(UUID userId) {
        User user = findUserById(userId);
        userRepository.delete(user);
        log.info("Usuário {} deletado", user.getEmail());
    }

    private User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + userId));
    }

    private Role findRoleById(UUID roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role não encontrada com ID: " + roleId));
    }

    private User getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException("Usuário não autenticado");
        }

        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário autenticado não encontrado"));
    }

}

