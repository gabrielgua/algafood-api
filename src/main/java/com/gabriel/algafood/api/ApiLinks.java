package com.gabriel.algafood.api;

import com.gabriel.algafood.api.controller.*;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ApiLinks {

    private static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    private static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("view", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    public Link linkToPedidos(String rel) {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("clienteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        String pedidosUrl = linkTo(PedidoController.class).toUri().toString();
        return Link.of(UriTemplate.of(pedidosUrl, PAGINACAO_VARIABLES.concat(filtroVariables)), rel);
    }

    public Link linkToPedidos() {
        return linkToPedidos(IanaLinkRelations.SELF_VALUE);
    }

    public Link linkToConfirmacaoPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(StatusPedidoController.class).confirmarPedido(codigoPedido)).withRel(rel);
    }

    public Link linkToEntregarPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(StatusPedidoController.class).confirmarEntrega(codigoPedido)).withRel(rel);
    }

    public Link linkToCancelarPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(StatusPedidoController.class).cancelarPedido(codigoPedido)).withRel(rel);
    }

    public Link linkToRestaurantes(String rel) {
        String restaurantesUrl = linkTo(RestauranteController.class).toUri().toString();
        return Link.of(UriTemplate.of(restaurantesUrl, PROJECAO_VARIABLES), rel);
    }

    public Link linkToRestaurantes() {
        return linkToRestaurantes(IanaLinkRelations.SELF_VALUE);
    }

    public Link linkToRestauranteAbertura(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class).abrirRestaurante(restauranteId)).withRel(rel);
    }

    public Link linkToRestauranteFechamento(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class).fecharRestaurante(restauranteId)).withRel(rel);
    }

    public Link linkToRestauranteInativacao(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class).inativar(restauranteId)).withRel(rel);
    }

    public Link linkToRestauranteAtivacao(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class).ativar(restauranteId)).withRel(rel);
    }


    public Link linkToRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .buscarPorId(restauranteId)).withRel(rel);
    }

    public Link linkToRestaurante(Long restauranteId) {
        return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToUsuario(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioController.class)
                .buscarPorId(usuarioId)).withRel(rel);
    }

    public Link linkToUsuario(Long usuarioId) {
        return linkToUsuario(usuarioId, IanaLinkRelations.SELF.value());
    }

    public Link linkToUsuarios(String rel) {
        return linkTo(UsuarioController.class).withRel(rel);
    }

    public Link linkToUsuarios() {
        return linkToUsuarios(IanaLinkRelations.SELF.value());
    }

    public Link linkToGruposUsuario(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class)
                .listarGrupos(usuarioId)).withRel(rel);
    }

    public Link linkToGruposUsuario(Long usuarioId) {
        return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormasPagamento(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class).listar(restauranteId)).withRel(rel);
    }

    public Link linkToRestauranteFormasPagamento(Long restauranteId) {
        return linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF_VALUE);
    }


    public Link linkToResponsaveisRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteUsuarioController.class)
                .listarResponsaveis(restauranteId)).withRel(rel);
    }

    public Link linkToResponsaveisRestaurante(Long restauranteId) {
        return linkToResponsaveisRestaurante(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
        return linkTo(methodOn(FormaPagamentoController.class)
                .buscarPorId(formaPagamentoId, null)).withRel(rel);
    }

    public Link linkToFormaPagamento(Long formaPagamentoId) {
        return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToFormaPagamentos(String rel) {
        return linkTo(FormaPagamentoController.class).withRel(rel);
    }

    public Link linkToFormaPagamentos() {
        return linkToFormaPagamentos(IanaLinkRelations.SELF_VALUE);
    }

    public Link linkToCidade(Long cidadeId, String rel) {
        return linkTo(methodOn(CidadeController.class)
                .buscarPorId(cidadeId)).withRel(rel);
    }

    public Link linkToCidade(Long cidadeId) {
        return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCidades(String rel) {
        return linkTo(CidadeController.class).withRel(rel);
    }

    public Link linkToCidades() {
        return linkToCidades(IanaLinkRelations.SELF.value());
    }

    public Link linkToEstado(Long estadoId, String rel) {
        return linkTo(methodOn(EstadoController.class)
                .buscarPorId(estadoId)).withRel(rel);
    }

    public Link linkToEstado(Long estadoId) {
        return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToEstados(String rel) {
        return linkTo(EstadoController.class).withRel(rel);
    }

    public Link linkToEstados() {
        return linkToEstados(IanaLinkRelations.SELF.value());
    }

    public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .buscarPorId(restauranteId, produtoId))
                .withRel(rel);
    }

    public Link linkToProduto(Long restauranteId, Long produtoId) {
        return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinha(Long cozinhaId) {
        return linkTo(methodOn(CozinhaController.class).buscarPorId(cozinhaId)).withSelfRel();
    }

    public Link linkToCozinhas(String rel) {
        return linkTo(CozinhaController.class).withRel(rel);
    }

    public Link linkToCozinhas() {
        return linkToCozinhas(IanaLinkRelations.SELF.value());
    }
}