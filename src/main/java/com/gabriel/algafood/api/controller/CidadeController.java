package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.CidadeAssembler;
import com.gabriel.algafood.api.exceptionhandler.Problem;
import com.gabriel.algafood.api.model.CidadeModel;
import com.gabriel.algafood.api.model.request.CidadeRequest;
import com.gabriel.algafood.domain.exception.EstadoNaoEncontradoException;
import com.gabriel.algafood.domain.exception.NegocioException;
import com.gabriel.algafood.domain.model.Cidade;
import com.gabriel.algafood.domain.service.CidadeService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
@RestController
@AllArgsConstructor
@RequestMapping("cidades")
public class CidadeController {

    private CidadeService service;
    private CidadeAssembler assembler;

    @ApiOperation("Lista as cidades")
    @GetMapping
    public List<CidadeModel> listar() {
        return assembler.toCollectionModel(service.listar());
    }

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @GetMapping("/{id}")
    public CidadeModel buscarPorId(
            @ApiParam(value = "ID de uma cidade", example = "1")
            @PathVariable Long id) {
        return assembler.toModel(service.buscarPorId(id));
    }

    @ApiOperation("Cadastra uma cidade")
    @ApiResponse(code = 201, message = "Cidade cadastrada")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel salvar(
            @ApiParam(name = "Corpo", value = "Representação de uma nova cidade")
            @RequestBody @Valid CidadeRequest request) {
        try {
            Cidade cidade = assembler.toEntity(request);
            return assembler.toModel(service.salvar(cidade));
        } catch (EstadoNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @PutMapping("/{id}")
    public CidadeModel editar(
            @ApiParam(name = "Corpo", value = "Representação de uma cidade com os novos dados")
            @RequestBody @Valid CidadeRequest request,
            @ApiParam(value = "ID de uma cidade", example = "2")
            @PathVariable Long id) {
        try {
            Cidade cidadeAtual = service.buscarPorId(id);
            assembler.copyToEntity(request, cidadeAtual);
            return assembler.toModel(service.salvar(cidadeAtual));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @ApiOperation("Remove uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade removida"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(
            @ApiParam("ID de uma cidade")
            @PathVariable Long id) {
        Cidade cidade = service.buscarPorId(id);
        service.remover(cidade.getId());
        return ResponseEntity.noContent().build();
    }
}
