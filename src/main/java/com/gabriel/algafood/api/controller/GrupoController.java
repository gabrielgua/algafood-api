package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.GrupoAssembler;
import com.gabriel.algafood.api.model.GrupoModel;
import com.gabriel.algafood.api.model.request.GrupoRequest;
import com.gabriel.algafood.domain.model.Grupo;
import com.gabriel.algafood.domain.service.GrupoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("grupos")
public class GrupoController {

    private GrupoService service;
    private GrupoAssembler assembler;

    @GetMapping
    public List<GrupoModel> listar() {
        return assembler.toCollectionList(service.listar());
    }

    @GetMapping("/{id}")
    public GrupoModel buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.buscarPorId(id));
    }

    @PostMapping
    public GrupoModel salvar(@RequestBody @Valid GrupoRequest request) {
        Grupo grupo = assembler.toEntity(request);
        return assembler.toModel(service.salvar(grupo));
    }

    @PutMapping("/{id}")
    public GrupoModel editar(@PathVariable Long id, @RequestBody @Valid GrupoRequest request) {
        Grupo grupo = service.buscarPorId(id);
        assembler.copyToEntity(request, grupo);
        return assembler.toModel(service.salvar(grupo));
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }

}
