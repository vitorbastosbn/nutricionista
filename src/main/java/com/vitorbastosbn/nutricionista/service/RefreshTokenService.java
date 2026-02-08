package com.vitorbastosbn.nutricionista.service;

import com.vitorbastosbn.nutricionista.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    /**
     * Valida o refresh token JWT e retorna os detalhes do usuário
     */
    public UserDetails validateRefreshToken(String token) {
        try {
            // Extrai o username do refresh token
            String username = jwtTokenProvider.extractUsername(token);

            // Carrega os detalhes do usuário
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Valida se o token é válido para este usuário
            if (!jwtTokenProvider.validateToken(token, userDetails)) {
                throw new BusinessException("Refresh token inválido ou expirado");
            }

            return userDetails;
        } catch (Exception ex) {
            throw new BusinessException("Refresh token inválido: " + ex.getMessage());
        }
    }
}

