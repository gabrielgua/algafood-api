package com.gabriel.algafood.api.openapi.controller;

import com.gabriel.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(tags = "Pedidos")
public interface StatusPedidoControllerOpenApi {

    @ApiOperation("Confirma um pedido pelo código")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido confirmado"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> confirmarPedido(@ApiParam(value = "Código do pedido", required = true) String codigoPedido);

    @ApiOperation("Cancela um pedido pelo código")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido cancelado"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> cancelarPedido(@ApiParam(value = "Código do pedido", required = true) String codigoPedido);

    @ApiOperation("Confirma a entrega de um pedido pelo código")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Entrega do pedido confirmada"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> confirmarEntrega(@ApiParam(value = "Código do pedido", required = true) String codigoPedido);
}
