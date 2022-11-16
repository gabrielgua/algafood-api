package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.ResourceUriHelper;
import com.gabriel.algafood.api.assembler.CidadeAssembler;
import com.gabriel.algafood.api.model.CidadeModel;
import com.gabriel.algafood.api.model.request.CidadeRequest;
import com.gabriel.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.gabriel.algafood.domain.exception.EstadoNaoEncontradoException;
import com.gabriel.algafood.domain.exception.NegocioException;
import com.gabriel.algafood.domain.model.Cidade;
import com.gabriel.algafood.domain.service.CidadeService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@AllArgsConstructor
@RequestMapping("cidades")
public class CidadeController implements CidadeControllerOpenApi {

    private CidadeService service;
    private CidadeAssembler assembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<CidadeModel> listar() {
        return assembler.toCollectionModel(service.listar());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeModel buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.buscarPorId(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel salvar(@RequestBody @Valid CidadeRequest request) {
        try {
            Cidade cidade = assembler.toEntity(request);
            var cidadeModel = assembler.toModel(service.salvar(cidade));

            ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());
            return cidadeModel;
        } catch (EstadoNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeModel editar(@RequestBody @Valid CidadeRequest request, @PathVariable Long id) {
        try {
            Cidade cidadeAtual = service.buscarPorId(id);
            assembler.copyToEntity(request, cidadeAtual);
            return assembler.toModel(service.salvar(cidadeAtual));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        Cidade cidade = service.buscarPorId(id);
        service.remover(cidade.getId());
    }
}
