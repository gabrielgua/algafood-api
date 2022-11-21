package com.gabriel.algafood.api.openapi.controller;

import com.gabriel.algafood.api.exceptionhandler.Problem;
import com.gabriel.algafood.api.model.FormaPagamentoModel;
import com.gabriel.algafood.api.model.request.FormaPagamentoRequest;
import com.gabriel.algafood.api.openapi.model.FormasPagamentoModelOpenApi;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

@Api(tags = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi {


    @ApiOperation(value = "Lista as formas de pagamento")
    @ApiResponse(code = 200, message = "OK", response = FormasPagamentoModelOpenApi.class)
    ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request);

    @ApiOperation("Busca uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    ResponseEntity<FormaPagamentoModel> buscarPorId(@ApiParam(value = "ID da forma de pagamento", example = "1") Long id, ServletWebRequest request);

    @ApiOperation("Cadastra uma forma de pagamento")
    @ApiResponse(code = 201, message = "Forma de pagamento cadastrada")
    FormaPagamentoModel salvar(@ApiParam(name = "Corpo", value = "Representação de uma nova forma de pagamento") FormaPagamentoRequest request);

    @ApiOperation("Atualiza uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    FormaPagamentoModel editar(
            @ApiParam(value = "ID de uma forma de pagamento", example = "1") Long id,
            @ApiParam(name = "Corpo", value = "Representação de uma forma de pagamento com novos dados") FormaPagamentoRequest request);

    @ApiOperation("Remove uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de pagamento removida"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de uma forma de pagamento") Long id);
}
