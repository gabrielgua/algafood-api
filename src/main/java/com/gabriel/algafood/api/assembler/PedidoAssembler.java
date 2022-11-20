package com.gabriel.algafood.api.assembler;

import com.gabriel.algafood.api.ApiLinks;
import com.gabriel.algafood.api.controller.*;
import com.gabriel.algafood.api.model.PedidoModel;
import com.gabriel.algafood.api.model.request.PedidoRequest;
import com.gabriel.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks apiLinks;

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


        pedidoModel.add(apiLinks.linkToPedidos());

        pedidoModel.getFormaPagamento().add(apiLinks.linkToFormaPagamento(formaPagamentoId));

        pedidoModel.getRestaurante().add(apiLinks.linkToRestaurante(restauranteId));

        pedidoModel.getCliente().add(apiLinks.linkToUsuario(usuarioId));

        pedidoModel.getEnderecoEntrega().getCidade().add(apiLinks.linkToCidade(cidadeId));

        pedidoModel.getItens().forEach(item -> item.add(apiLinks.linkToProduto(restauranteId, item.getProdutoId())));

        return pedidoModel;
    }

    public Pedido toEntity(PedidoRequest request) {
        return modelMapper.map(request, Pedido.class);
    }

}
