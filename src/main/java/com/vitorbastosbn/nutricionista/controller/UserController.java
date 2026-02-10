package com.vitorbastosbn.nutricionista.controller;

import com.vitorbastosbn.nutricionista.controller.doc.UserControllerAPI;
import com.vitorbastosbn.nutricionista.domain.dto.request.UpdateUserRequest;
import com.vitorbastosbn.nutricionista.domain.dto.request.UpdateUserAddressRequest;
import com.vitorbastosbn.nutricionista.domain.dto.request.UpdateUserContactRequest;
import com.vitorbastosbn.nutricionista.domain.dto.response.UserResponse;
import com.vitorbastosbn.nutricionista.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserControllerAPI {

    private final UserService userService;

    @Override
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        UserResponse user = userService.getCurrentUser();
        return ResponseEntity.ok(user);
    }

    @Override
    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN') or #userId == principal.id")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID userId) {
        UserResponse user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String search) {

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
        Page<UserResponse> users = userService.getAllUsers(fullName, email, search, pageable);
        return ResponseEntity.ok(users);
    }

    @Override
    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateCurrentUser(@RequestBody @Valid UpdateUserRequest request) {
        UserResponse user = userService.updateCurrentUser(request);
        return ResponseEntity.ok(user);
    }

    @Override
    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable UUID userId,
            @RequestBody @Valid UpdateUserRequest request) {
        UserResponse user = userService.updateUser(userId, request);
        return ResponseEntity.ok(user);
    }

    @Override
    @PutMapping("/me/address")
    public ResponseEntity<UserResponse> updateCurrentUserAddress(
            @RequestBody @Valid UpdateUserAddressRequest request) {
        UserResponse user = userService.updateCurrentUserAddress(request);
        return ResponseEntity.ok(user);
    }

    @Override
    @PutMapping("/{userId}/address")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUserAddress(
            @PathVariable UUID userId,
            @RequestBody @Valid UpdateUserAddressRequest request) {
        UserResponse user = userService.updateUserAddress(userId, request);
        return ResponseEntity.ok(user);
    }

    @Override
    @PutMapping("/me/contact")
    public ResponseEntity<UserResponse> updateCurrentUserContact(
            @RequestBody @Valid UpdateUserContactRequest request) {
        UserResponse user = userService.updateCurrentUserContact(request);
        return ResponseEntity.ok(user);
    }

    @Override
    @PutMapping("/{userId}/contact")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUserContact(
            @PathVariable UUID userId,
            @RequestBody @Valid UpdateUserContactRequest request) {
        UserResponse user = userService.updateUserContact(userId, request);
        return ResponseEntity.ok(user);
    }

    @Override
    @PutMapping("/{userId}/roles/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> addRoleToUser(
            @PathVariable UUID userId,
            @PathVariable UUID roleId) {
        UserResponse user = userService.addRoleToUser(userId, roleId);
        return ResponseEntity.ok(user);
    }

    @Override
    @DeleteMapping("/{userId}/roles/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> removeRoleFromUser(
            @PathVariable UUID userId,
            @PathVariable UUID roleId) {
        UserResponse user = userService.removeRoleFromUser(userId, roleId);
        return ResponseEntity.ok(user);
    }

    @Override
    @PutMapping("/{userId}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUserRoles(
            @PathVariable UUID userId,
            @RequestBody Set<UUID> roleIds) {
        UserResponse user = userService.updateUserRoles(userId, roleIds);
        return ResponseEntity.ok(user);
    }

    @Override
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCurrentUser() {
        userService.deleteCurrentUser();
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}



