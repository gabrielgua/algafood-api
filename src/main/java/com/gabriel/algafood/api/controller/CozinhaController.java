package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.CozinhaAssembler;
import com.gabriel.algafood.api.model.CozinhaModel;
import com.gabriel.algafood.api.model.request.CozinhaRequest;
import com.gabriel.algafood.domain.model.Cozinha;
import com.gabriel.algafood.core.validation.service.CozinhaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("cozinhas")
public class CozinhaController {

    private CozinhaService service;

    private CozinhaAssembler assembler;

    @GetMapping
    public List<CozinhaModel> listar() {
        return assembler.toCollectionModel(service.listar());
    }

    @GetMapping("/{id}")
    public CozinhaModel buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModel adicionar(@RequestBody @Valid CozinhaRequest request) {
        Cozinha cozinha = assembler.toEntity(request);
        return assembler.toModel(service.salvar(cozinha));
    }

    @PutMapping("/{id}")
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
