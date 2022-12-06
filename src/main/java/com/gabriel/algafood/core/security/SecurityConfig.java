package com.gabriel.algafood.core.security;

import com.gabriel.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfig {

    @Autowired
    private RestauranteRepository repository;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUsuarioId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();
        return Long.valueOf(jwt.getClaimAsString("usuario_id"));
    }

    public boolean gerenciaRestaurante(Long restauranteId) {
        return repository.isResponsavel(restauranteId, getUsuarioId());
    }
}
