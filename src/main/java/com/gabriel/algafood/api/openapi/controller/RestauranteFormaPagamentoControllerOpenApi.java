package com.gabriel.algafood.api.openapi.controller;

import com.gabriel.algafood.api.exceptionhandler.Problem;
import com.gabriel.algafood.api.model.FormaPagamentoModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

    @ApiOperation("Lista as formas de pagamento de um determinado restaurante pelo ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    CollectionModel<FormaPagamentoModel> listar(@ApiParam(value = "ID de um restaurante", required = true) Long restauranteId);

    @ApiOperation("Desvincula uma forma de pagamento com um restaurante pelos IDs")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de pagamento desvinculada"),
            @ApiResponse(code = 404, message = "Forma de pagamento ou restaurante não encontrados")
    })
    void desvincular(
            @ApiParam(value = "ID do restaurante", required = true) Long restauranteId,
            @ApiParam(value = "ID da forma de pagamento", required = true) Long formaPagamentoId);

    @ApiOperation("Vincula uma forma de pagamento com um restaurante pelos IDs")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de pagamento vinculada"),
            @ApiResponse(code = 404, message = "Forma de pagamento ou restaurante não encontrados")
    })
    void vincular(
            @ApiParam(value = "ID do restaurante", required = true) Long restauranteId,
            @ApiParam(value = "ID da forma de pagamento", required = true) Long formaPagamentoId);
}
