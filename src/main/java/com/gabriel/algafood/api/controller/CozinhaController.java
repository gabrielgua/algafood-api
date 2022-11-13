package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.CozinhaAssembler;
import com.gabriel.algafood.api.model.CozinhaModel;
import com.gabriel.algafood.api.model.request.CozinhaRequest;
import com.gabriel.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.gabriel.algafood.domain.model.Cozinha;
import com.gabriel.algafood.domain.service.CozinhaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {

    private CozinhaService service;

    private CozinhaAssembler assembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cozinha> cozinhasPage = service.listar(pageable);
        List<CozinhaModel> cozinhasModel = assembler.toCollectionModel(cozinhasPage.getContent());
        Page<CozinhaModel> cozinhasModelPage = new PageImpl<>(cozinhasModel, pageable, cozinhasPage.getTotalElements());
        return cozinhasModelPage;
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
