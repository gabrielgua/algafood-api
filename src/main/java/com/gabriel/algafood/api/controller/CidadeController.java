package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.CidadeAssembler;
import com.gabriel.algafood.api.model.CidadeModel;
import com.gabriel.algafood.api.model.request.CidadeRequest;
import com.gabriel.algafood.domain.exception.EstadoNaoEncontradoException;
import com.gabriel.algafood.domain.exception.NegocioException;
import com.gabriel.algafood.domain.model.Cidade;
import com.gabriel.algafood.domain.service.CidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @GetMapping("/{id}")
    public CidadeModel buscarPorId(
            @ApiParam(value = "ID de uma cidade", example = "1")
            @PathVariable Long id) {
        return assembler.toModel(service.buscarPorId(id));
    }

    @ApiOperation("Cadastra uma cidade")
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(
            @ApiParam("ID de uma cidade")
            @PathVariable Long id) {
        Cidade cidade = service.buscarPorId(id);
        service.remover(cidade.getId());
        return ResponseEntity.noContent().build();
    }
}
