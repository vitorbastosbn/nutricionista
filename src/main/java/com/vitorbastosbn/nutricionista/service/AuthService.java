package com.vitorbastosbn.nutricionista.service;

import com.vitorbastosbn.nutricionista.domain.dto.request.LoginRequest;
import com.vitorbastosbn.nutricionista.domain.dto.request.RegisterRequest;
import com.vitorbastosbn.nutricionista.domain.dto.request.RefreshTokenRequest;
import com.vitorbastosbn.nutricionista.domain.dto.response.AuthResponse;
import com.vitorbastosbn.nutricionista.entity.Role;
import com.vitorbastosbn.nutricionista.entity.User;
import com.vitorbastosbn.nutricionista.exception.BusinessException;
import com.vitorbastosbn.nutricionista.mapper.UserMapper;
import com.vitorbastosbn.nutricionista.repository.RoleRepository;
import com.vitorbastosbn.nutricionista.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> {
                    log.warn("Tentativa de login com email não encontrado: {}", request.email());
                    return new BusinessException("Email ou senha inválidos");
                });

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            log.warn("Tentativa de login com senha inválida para email: {}", request.email());
            throw new BusinessException("Email ou senha inválidos");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

        log.info("Login bem-sucedido para usuário: {}", user.getEmail());
        return buildAuthResponse(user, accessToken, refreshToken);
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            log.warn("Tentativa de registro com email já existente: {}", request.email());
            throw new BusinessException("Este email já está cadastrado no sistema");
        }

        User user = userMapper.toUserEntity(request);

        String encryptedPassword = passwordEncoder.encode(request.password());
        user.setPassword(encryptedPassword);

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new BusinessException("Role padrão 'ROLE_USER' não encontrada"));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        user = userRepository.save(user);
        log.info("Novo usuário registrado: {}", user.getEmail());

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

        return buildAuthResponse(user, accessToken, refreshToken);
    }

    public AuthResponse refreshAccessToken(RefreshTokenRequest request) {
        UserDetails userDetails = refreshTokenService.validateRefreshToken(request.refreshToken());

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        String newAccessToken = jwtTokenProvider.generateAccessToken(userDetails);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

        log.info("Refresh token renovado para usuário: {}", user.getEmail());
        return buildAuthResponse(user, newAccessToken, newRefreshToken);
    }

    private AuthResponse buildAuthResponse(User user, String accessToken, String refreshToken) {
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtTokenProvider.getAccessTokenExpiration())
                .refreshExpiresIn(jwtTokenProvider.getRefreshTokenExpiration())
                .userId(user.getId().toString())
                .username(user.getEmail())
                .email(user.getEmail())
                .issuedAt(LocalDateTime.now())
                .build();
    }
}

