package com.gabriel.algafood.core.security;

import com.gabriel.algafood.domain.repository.PedidoRepository;
import com.gabriel.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfig {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUsuarioId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();

        return jwt.getClaim("usuario_id");
    }

    public boolean isAutenticado() {
        return getAuthentication().isAuthenticated();
    }

    public boolean temEscopoLeitura() {
        return hasAuthority("SCOPE_READ");
    }

    public boolean temEscopoEscrita() {
        return hasAuthority("SCOPE_WRITE");
    }

    // Cozinhas
    public boolean podeConsultarCozinhas() {
        return temEscopoLeitura() && isAutenticado();
    }

    public boolean podeEditarCozinhas() {
        return temEscopoEscrita() && hasAuthority("EDITAR_COZINHAS");
    }
    // Restaurantes
    public boolean podeConsultarRestaurantes() {
        return temEscopoLeitura() && isAutenticado();
    }

    public boolean podeGerenciarCadastroRestaurantes() {
        return temEscopoEscrita() && hasAuthority("EDITAR_RESTAURANTES");
    }

    public boolean podeGerenciarFuncionamentoRestaurantes(Long restauranteId) {
        return temEscopoEscrita() && (hasAuthority("EDITAR_RESTAURANTES")
                || gerenciaRestaurante(restauranteId));
    }


    // Usuarios - Grupos - Permissoes
    public boolean podeConsultarUsuariosGruposPermissoes() {
        return temEscopoLeitura() && hasAuthority("CONSULTAR_USUARIOS_GRUPOS_PERMISSOES");
    }

    public boolean podeEditarUsuariosGruposPermissoes() {
        return temEscopoEscrita() && hasAuthority("EDITAR_USUARIOS_GRUPOS_PERMISSOES");
    }

    public boolean podeAlterarPropriaSenha(Long usuarioId) {
        return temEscopoEscrita() && usuarioAutenticadoIgual(usuarioId);
    }

    public boolean podeAlterarUsuario(Long usuarioId) {
        return temEscopoEscrita() && (hasAuthority("EDITAR_USUARIOS_GRUPOS_PERMISSOES")
            || usuarioAutenticadoIgual(usuarioId));
    }

    public boolean podeConsultarUsuario(Long usuarioId) {
        return temEscopoLeitura() && (hasAuthority("CONSULTAR_USUARIOS_GRUPOS_PERMISSOES")
            || usuarioAutenticadoIgual(usuarioId));
    }

    public boolean podeCadastrarUsuarios() {
        return podeEditarUsuariosGruposPermissoes() || !isAutenticado();
    }


    // Pedidos
    public boolean podeBuscarPedidos(Long usuarioId, Long restauranteId) {
        return temEscopoLeitura() && (hasAuthority("CONSULTAR_PEDIDOS")
                || usuarioAutenticadoIgual(usuarioId)
                || gerenciaRestaurante(restauranteId));
    }

    public boolean podeBuscarPedidos() {
        return temEscopoLeitura() && isAutenticado();
    }

    // Formas de Pagamento
    public boolean podeConsultarFormasDePagamento() {
        return temEscopoLeitura() && isAutenticado();
    }

    public boolean podeEditarFormasDePagamento() {
        return temEscopoEscrita() && hasAuthority("EDITAR_FORMAS_PAGAMENTO");
    }

    // Cidades
    public boolean podeConsultarCidades() {
        return temEscopoLeitura() && isAutenticado();
    }

    public boolean podeEditarCidades() {
        return temEscopoEscrita() && hasAuthority("EDITAR_CIDADES");
    }

    // Estados
    public boolean podeConsultarEstados() {
        return temEscopoLeitura() && isAutenticado();
    }

    public boolean podeEditarEstados() {
        return temEscopoEscrita() && hasAuthority("EDITAR_ESTADOS");
    }

    // Estatísticas
    public boolean podeConsultarEstatisticas() {
        return temEscopoLeitura() && hasAuthority("GERAR_RELATORIOS");
    }

    public boolean hasAuthority(String authority) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(authority));
    }

    public boolean gerenciaRestaurante(Long restauranteId) {
        if (restauranteId == null) return false;
        return restauranteRepository.isResponsavel(restauranteId, getUsuarioId());
    }

    public boolean gerenciaRestauranteDoPedido(String codigoPedido) {
        return pedidoRepository.isResponsavelPorPedido(codigoPedido, getUsuarioId());
    }

    public boolean usuarioAutenticadoIgual(Long usuarioId) {
        return getUsuarioId() != null && usuarioId != null && getUsuarioId().equals(usuarioId);
    }



    public boolean podeGerenciarPedidos(String codigoPedido) {
        return hasAuthority("SCOPE_WRITE") && (hasAuthority("GERENCIAR_PEDIDOS")
                || gerenciaRestauranteDoPedido(codigoPedido));
    }

}
