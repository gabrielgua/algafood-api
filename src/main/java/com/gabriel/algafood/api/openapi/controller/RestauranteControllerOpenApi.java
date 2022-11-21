package com.gabriel.algafood.api.openapi.controller;

import com.gabriel.algafood.api.exceptionhandler.Problem;
import com.gabriel.algafood.api.model.RestauranteApenasNomeModel;
import com.gabriel.algafood.api.model.RestauranteBasicoModel;
import com.gabriel.algafood.api.model.RestauranteModel;
import com.gabriel.algafood.api.model.request.RestauranteRequest;
import com.gabriel.algafood.api.openapi.model.RestauranteBasicoModelOpenApi;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

    @ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nome da projeção de restaurantes", allowableValues = "nome",
                    name = "view", paramType = "query", type = "string")
    })
    CollectionModel<RestauranteBasicoModel> listar();

    @ApiOperation(value = "Lista restaurantes", hidden = true)
    CollectionModel<RestauranteApenasNomeModel> listarApenasNome();

    @ApiOperation("Busca um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),

    })
    RestauranteModel buscarPorId(@ApiParam(value = "ID do restaurante", required = true) Long id);

    @ApiOperation("Cadastra um novo restaurante")
    @ApiResponse(code = 201, message = "Restaurante cadastrado")
    RestauranteModel salvar(@ApiParam(name = "Corpo", value = "Representação de um novo restaurante", required = true) RestauranteRequest restauranteRequest);

    @ApiOperation("Atualiza um restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Restaurante atualizado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    RestauranteModel editar(
            @ApiParam(name = "Corpo", value = "Representação de um restaurante com novos dados", required = true) RestauranteRequest restauranteRequest,
            @ApiParam(value = "ID de um restaurante", required = true) Long id);


    @ApiOperation("Remove um restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante removido"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de um restaurante", required = true) Long id);

    @ApiOperation("Ativa um restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante ativado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> ativar(@ApiParam(value = "ID de um restaurante", required = true) Long id);

    @ApiOperation("Inativa um restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante inativado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> inativar(@ApiParam(value = "Id de um restaurante", required = true) Long id);

    @ApiOperation("Ativa múltiplos restaurantes")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurantes ativados"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    void ativarMultiplos(@ApiParam(value = "Lista com IDs de restaurantes", required = true) List<Long> restauranteIds);

    @ApiOperation("Inativa múltiplos restaurantes")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurantes inativados"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    void invativarMultiplos(@ApiParam(value = "Lista com IDs de restaurantes", required = true) List<Long> restauranteIds);

    @ApiOperation("Ativa ou inativa um restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Aplica o status de ativação oposto ao atual"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    void ativarOuInativar(@ApiParam(value = "ID de um restaurante", required = true) Long id);

    @ApiOperation("Abre um restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante aberto"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> abrirRestaurante(@ApiParam(value = "ID de um restaurante", required = true) Long id);

    @ApiOperation("Fecha um restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante fechado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> fecharRestaurante(@ApiParam(value = "ID de um restaurante", required = true) Long id);

    @ApiOperation("Abre ou fecha um restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Aplica o status de abertura oposto ao atual"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    void abrirOuFecharRestaurante(@ApiParam(value = "ID de um restaurante", required = true) Long id);

}
