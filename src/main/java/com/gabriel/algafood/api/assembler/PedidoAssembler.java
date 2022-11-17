package com.gabriel.algafood.api.assembler;

import com.gabriel.algafood.api.controller.*;
import com.gabriel.algafood.api.model.PedidoModel;
import com.gabriel.algafood.api.model.request.PedidoRequest;
import com.gabriel.algafood.domain.model.FormaPagamento;
import com.gabriel.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {
        var pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);

        var formaPagamentoId = pedidoModel.getFormaPagamento().getId();
        var restauranteId = pedidoModel.getRestaurante().getId();
        var usuarioId = pedidoModel.getCliente().getId();
        var cidadeId = pedidoModel.getEnderecoEntrega().getCidade().getId();


        //collection de pedidos
        pedidoModel.add(linkTo(PedidoController.class).withRel("pedidos"));
        //forma de pagamento
        pedidoModel.getFormaPagamento().add(linkTo(methodOn(FormaPagamentoController.class).buscarPorId(formaPagamentoId, null)).withSelfRel());
        //restaurante
        pedidoModel.getRestaurante().add(linkTo(methodOn(RestauranteController.class).buscarPorId(restauranteId)).withSelfRel());
        //cliente
        pedidoModel.getCliente().add(linkTo(methodOn(UsuarioController.class).buscarPorId(usuarioId)).withSelfRel());
        //endereco.cidade
        pedidoModel.getEnderecoEntrega().getCidade().add(linkTo(methodOn(CidadeController.class).buscarPorId(cidadeId)).withSelfRel());
        //itens.produto
        pedidoModel.getItens().forEach(item -> item.add(linkTo(methodOn(RestauranteProdutoController.class)
                .buscarPorId(restauranteId, item.getProdutoId())).withRel("produto")));

        return pedidoModel;
    }

    public Pedido toEntity(PedidoRequest request) {
        return modelMapper.map(request, Pedido.class);
    }

}
