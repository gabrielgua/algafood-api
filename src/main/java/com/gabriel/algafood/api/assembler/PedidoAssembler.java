package com.gabriel.algafood.api.assembler;

import com.gabriel.algafood.api.model.PedidoModel;
import com.gabriel.algafood.api.model.PedidoResumoModel;
import com.gabriel.algafood.api.model.request.PedidoRequest;
import com.gabriel.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoModel toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoModel.class);
    }

    public List<PedidoModel> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toModel(pedido))
                .collect(Collectors.toList());
    }

    public Pedido toEntity(PedidoRequest request) {
        return modelMapper.map(request, Pedido.class);
    }

    public PedidoResumoModel toResumoModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumoModel.class);
    }

    public List<PedidoResumoModel> toCollectionResumoModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toResumoModel(pedido))
                .collect(Collectors.toList());
    }
}