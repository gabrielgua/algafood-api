package com.gabriel.algafood.api.openapi.controller;

import com.gabriel.algafood.api.exceptionhandler.Problem;
import com.gabriel.algafood.api.model.PedidoModel;
import com.gabriel.algafood.api.model.PedidoResumoModel;
import com.gabriel.algafood.api.model.request.PedidoRequest;
import com.gabriel.algafood.domain.filter.PedidoFilter;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "Pedidos")
public interface PedidoControllerModelOpenApi {


    @ApiOperation("Lista os pedidos")
    Page<PedidoResumoModel> pesquisar(Pageable pageable, PedidoFilter filter);

    @ApiOperation("Busca um pedido por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do pedido inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    PedidoModel buscarPorId(@ApiParam(value = "Código do pedido", required = true) String codigoPedido);

    @ApiOperation("Cadastra um novo pedido")
    @ApiResponse(code = 201, message = "Pedido cadastrado")
    PedidoModel salvar(@ApiParam(name = "Corpo", value = "Representação de um novo pedido", required = true) PedidoRequest request);
}
