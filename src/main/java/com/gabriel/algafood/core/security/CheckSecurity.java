package com.gabriel.algafood.core.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    public @interface Cozinhas {

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('EDITAR_COZINHAS') and hasAuthority('SCOPE_WRITE')")
        public @interface PodeEditar {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("isAuthenticated() and hasAuthority('SCOPE_READ')")
        public @interface PodeConsultar {}


    }
}
