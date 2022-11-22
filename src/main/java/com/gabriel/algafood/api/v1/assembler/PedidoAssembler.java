package com.gabriel.algafood.api.v1.assembler;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.controller.PedidoController;
import com.gabriel.algafood.api.v1.model.PedidoModel;
import com.gabriel.algafood.api.v1.model.request.PedidoRequest;
import com.gabriel.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

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


        pedidoModel.add(links.linkToPedidos());

        if (pedido.podeSerConfirmado()) {
            pedidoModel.add(links.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
        }

        if (pedido.podeSerCancelado()) {
            pedidoModel.add(links.linkToCancelarPedido(pedido.getCodigo(), "cancelar"));
        }

        if (pedido.podeSerEntregue()) {
            pedidoModel.add(links.linkToEntregarPedido(pedido.getCodigo(), "entregar"));
        }

        pedidoModel.getFormaPagamento().add(links.linkToFormaPagamento(formaPagamentoId));
        pedidoModel.getRestaurante().add(links.linkToRestaurante(restauranteId));
        pedidoModel.getCliente().add(links.linkToUsuario(usuarioId));
        pedidoModel.getEnderecoEntrega().getCidade().add(links.linkToCidade(cidadeId));
        pedidoModel.getItens().forEach(item -> item.add(links.linkToProduto(restauranteId, item.getProdutoId())));

        return pedidoModel;
    }

    public Pedido toEntity(PedidoRequest request) {
        return modelMapper.map(request, Pedido.class);
    }

}
