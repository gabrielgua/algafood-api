package com.gabriel.algafood.api.v1.controller;

import com.gabriel.algafood.api.v1.assembler.CozinhaAssembler;
import com.gabriel.algafood.api.v1.model.CozinhaModel;
import com.gabriel.algafood.api.v1.model.request.CozinhaRequest;
import com.gabriel.algafood.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.gabriel.algafood.domain.model.Cozinha;
import com.gabriel.algafood.domain.service.CozinhaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {

    private CozinhaService service;
    private CozinhaAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedModel<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cozinha> cozinhasPage = service.listar(pageable);
        PagedModel<CozinhaModel> cozinhasPagedModel = pagedResourcesAssembler.toModel(cozinhasPage, assembler);

        return cozinhasPagedModel;

    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CozinhaModel buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.buscarPorId(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModel adicionar(@RequestBody @Valid CozinhaRequest request) {
        Cozinha cozinha = assembler.toEntity(request);
        return assembler.toModel(service.salvar(cozinha));
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CozinhaModel editar(@RequestBody @Valid CozinhaRequest request, @PathVariable Long id) {
        Cozinha cozinha = service.buscarPorId(id);
        assembler.copyToEntity(request, cozinha);
        return assembler.toModel(service.salvar(cozinha));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        Cozinha cozinha = service.buscarPorId(id);
        service.remover(cozinha.getId());
    }

















}
