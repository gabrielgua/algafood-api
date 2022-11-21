package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.ApiLinks;
import com.gabriel.algafood.api.assembler.PermissaoAssembler;
import com.gabriel.algafood.api.model.PermissaoModel;
import com.gabriel.algafood.domain.service.PermissaoService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("permissoes")
public class PermissaoController {

    private PermissaoService service;
    private PermissaoAssembler assembler;
    private ApiLinks links;


    @GetMapping
    public CollectionModel<PermissaoModel> listar() {
        return assembler.toCollectionModel(service.listar());
    }
}
