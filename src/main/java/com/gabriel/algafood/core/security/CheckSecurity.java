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
        @PreAuthorize("hasAuthority('EDITAR_COZINHAS') and hasAuthority('SCOPE_WRITE')")
        public @interface PodeEditar {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("isAuthenticated() and hasAuthority('SCOPE_READ')")
        public @interface PodeConsultar {}


    }

    public @interface Restaurantes {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('EDITAR_RESTAURANTES') and hasAuthority('SCOPE_WRITE')")
        public @interface PodeGerenciarCadastro {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("isAuthenticated() and hasAuthority('SCOPE_READ')")
        public @interface PodeConsultar {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES') or @securityConfig.gerenciaRestaurante(#restauranteId)")
        public @interface PodeGerenciarFuncionamento {}
    }

    public @interface Pedidos {

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') " +
                "or @securityConfig.usuarioAutenticadoIgual(returnObject.cliente.id)" +
                "or @securityConfig.gerenciaRestaurante(returnObject.restaurante.id)")
        public @interface PodeBuscar {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('CONSULTAR_PEDIDOS') " +
                "or @securityConfig.gerenciaRestaurante(#filter.restauranteId) " +
                "or @securityConfig.usuarioAutenticadoIgual(#filter.clienteId)")
        public @interface PodePesquisar {}

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
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        public @interface PodeConsultar {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        public @interface PodeEditar {}
    }

    public @interface Cidades {

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        public @interface PodeConsultar {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
        public @interface PodeEditar {}
    }

    public @interface Estados {

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        public @interface PodeConsultar {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
        public @interface PodeEditar {}
    }

    public @interface UsuariosGruposPermissoes {

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityConfig.usuarioAutenticadoIgual(#usuarioId)")
        public @interface PodeAlterarPropriaSenha {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') " +
                "or @securityConfig.usuarioAutenticadoIgual(#usuarioId)")
        public @interface PodeAlterarUsuario {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES')")
        public @interface PodeEditar {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('CONSULTAR_USUARIOS_GRUPOS_PERMISSOES')")
        public @interface PodeConsultar {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('CONSULTAR_USUARIOS_GRUPOS_PERMISSOES') " +
                "or @securityConfig.usuarioAutenticadoIgual(#usuarioId)")
        public @interface PodeConsultarUsuario {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') " +
                "or !isAuthenticated()")
        public @interface PodeCadastrar {}
    }

    public @interface Estatisticas {

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('GERAR_RELATORIOS')")
        public @interface PodeConsultar {}
    }
}
