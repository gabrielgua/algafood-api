package com.gabriel.algafood.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    public @interface Cozinhas {

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.podeEditarCozinhas()")
        public @interface PodeEditar {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.podeConsultarCozinhas()")
        public @interface PodeConsultar {}
    }

    public @interface Restaurantes {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.podeGerenciarCadastroRestaurantes()")
        public @interface PodeGerenciarCadastro {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.podeConsultarRestaurantes()")
        public @interface PodeConsultar {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.podeGerenciarFuncionamentoRestaurantes(#restauranteId)")
        public @interface PodeGerenciarFuncionamento {}
    }

    public @interface Pedidos {

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize("@securityConfig.podeBuscarPedidos(returnObject.cliente.id, returnObject.restaurante.id)")
        public @interface PodeBuscar {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.podeBuscarPedidos(#filter.clienteId, #filter.restauranteId)")
        public @interface PodeListar {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        public @interface PodeCriar {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.podeGerenciarPedidos(#codigoPedido)")
        public @interface PodeGerenciarPedidos {}

    }

    public @interface FormasPagamento {

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.podeConsultarFormasDePagamento()")
        public @interface PodeConsultar {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.podeEditarFormasDePagamento()")
        public @interface PodeEditar {}
    }

    public @interface Cidades {

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.podeConsultarCidades()")
        public @interface PodeConsultar {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.podeEditarCidades()")
        public @interface PodeEditar {}
    }

    public @interface Estados {

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.podeConsultarEstados()")
        public @interface PodeConsultar {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.podeEditarEstados()")
        public @interface PodeEditar {}
    }

    public @interface UsuariosGruposPermissoes {

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.podeAlterarPropriaSenha(#usuarioId)")
        public @interface PodeAlterarPropriaSenha {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.podeAlterarUsuario(#usuarioId)")
        public @interface PodeAlterarUsuario {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.podeConsultarUsuario(#usuarioId)")
        public @interface PodeConsultarUsuario {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.podeEditarUsuariosGruposPermissoes()")
        public @interface PodeEditar {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.podeConsultarUsuariosGruposPermissoes()")
        public @interface PodeConsultar {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.podeCadastrarUsuarios()")
        public @interface PodeCadastrarUsuarios {}
    }

    public @interface Estatisticas {

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.podeConsultarEstatisticas()")
        public @interface PodeConsultar {}
    }
}
