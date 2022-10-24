package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.CozinhaAssembler;
import com.gabriel.algafood.api.model.CozinhaModel;
import com.gabriel.algafood.api.model.request.CozinhaRequest;
import com.gabriel.algafood.domain.model.Cozinha;
import com.gabriel.algafood.domain.service.CozinhaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public Page<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cozinha> cozinhasPage = service.listar(pageable);
        List<CozinhaModel> cozinhasModel = assembler.toCollectionModel(cozinhasPage.getContent());
        Page<CozinhaModel> cozinhasModelPage = new PageImpl<>(cozinhasModel, pageable, cozinhasPage.getTotalElements());
        return cozinhasModelPage;
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
