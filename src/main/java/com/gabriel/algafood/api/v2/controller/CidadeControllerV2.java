package com.gabriel.algafood.api.v2.controller;

import com.gabriel.algafood.api.ResourceUriHelper;
import com.gabriel.algafood.api.v2.assembler.CidadeAssemblerV2;
import com.gabriel.algafood.api.v2.model.CidadeModelV2;
import com.gabriel.algafood.api.v2.model.request.CidadeRequestV2;
import com.gabriel.algafood.core.web.ApiMediaTypes;
import com.gabriel.algafood.domain.exception.EstadoNaoEncontradoException;
import com.gabriel.algafood.domain.exception.NegocioException;
import com.gabriel.algafood.domain.service.CidadeService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/v2/cidades")
public class CidadeControllerV2 {

    private CidadeService service;
    private CidadeAssemblerV2 assemblerV2;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<CidadeModelV2> listar() {
        return assemblerV2.toCollectionModel(service.listar());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeModelV2 buscarPorId(@PathVariable Long id) {
        return assemblerV2.toModel(service.buscarPorId(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeModelV2 salvar(@RequestBody @Valid CidadeRequestV2 requestV2) {
        try {
            var cidade = assemblerV2.toEntity(requestV2);
            var cidadeModelV2 = assemblerV2.toModel(service.salvar(cidade));
            ResourceUriHelper.addUriInResponseHeader(cidadeModelV2.getIdCidade());
            return cidadeModelV2;
        } catch (EstadoNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeModelV2 editar(@PathVariable Long id, @RequestBody @Valid CidadeRequestV2 requestV2) {
        try {
            var cidadeAtual = service.buscarPorId(id);
            assemblerV2.copyToEntity(requestV2, cidadeAtual);
            return assemblerV2.toModel(service.salvar(cidadeAtual));
        } catch (EstadoNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        var cidade = service.buscarPorId(id);
        service.remover(cidade.getId());
    }
}
