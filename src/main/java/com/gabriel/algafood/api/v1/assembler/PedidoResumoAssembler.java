package com.gabriel.algafood.api.v1.assembler;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.controller.PedidoController;
import com.gabriel.algafood.api.v1.model.PedidoResumoModel;
import com.gabriel.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    public PedidoResumoAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        var pedidoResumoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoResumoModel);

        var restauranteId = pedidoResumoModel.getRestaurante().getId();
        var usuarioId = pedidoResumoModel.getCliente().getId();

        pedidoResumoModel.add(links.linkToPedidos());
        pedidoResumoModel.getRestaurante().add(links.linkToRestaurante(restauranteId));
        pedidoResumoModel.getCliente().add(links.linkToUsuario(usuarioId));

        return pedidoResumoModel;
    }
}
