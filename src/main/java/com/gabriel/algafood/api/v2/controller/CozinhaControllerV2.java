package com.gabriel.algafood.api.v2.controller;

import com.gabriel.algafood.api.v2.assembler.CozinhaAssemblerV2;
import com.gabriel.algafood.api.v2.model.CozinhaModelV2;
import com.gabriel.algafood.api.v2.model.request.CozinhaRequestV2;
import com.gabriel.algafood.api.v2.openapi.controller.CozinhaControllerOpenApiV2;
import com.gabriel.algafood.domain.model.Cozinha;
import com.gabriel.algafood.domain.service.CozinhaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/v2/cozinhas")
public class CozinhaControllerV2 implements CozinhaControllerOpenApiV2 {

    private CozinhaService service;
    private CozinhaAssemblerV2 assemblerV2;
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<CozinhaModelV2> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<Cozinha> cozinhas = service.listar(pageable);
        return pagedResourcesAssembler.toModel(cozinhas, assemblerV2);
    }

    @GetMapping("/{id}")
    public CozinhaModelV2 buscarPorId(@PathVariable Long id) {
        return assemblerV2.toModel(service.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModelV2 salvar(@RequestBody @Valid CozinhaRequestV2 requestV2) {
        var cozinha = assemblerV2.toEntity(requestV2);
        return assemblerV2.toModel(service.salvar(cozinha));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CozinhaModelV2 editar(@PathVariable Long id, @RequestBody @Valid CozinhaRequestV2 requestV2) {
        var cozinha = service.buscarPorId(id);
        assemblerV2.copyToEntity(requestV2, cozinha);
        return assemblerV2.toModel(service.salvar(cozinha));
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        var cozinha = service.buscarPorId(id);
        service.remover(cozinha.getId());
    }
}
