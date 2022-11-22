package com.gabriel.algafood.api.v1.controller;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.assembler.PermissaoAssembler;
import com.gabriel.algafood.api.v1.model.PermissaoModel;
import com.gabriel.algafood.api.v1.openapi.controller.PermissaoControllerOpenApi;
import com.gabriel.algafood.domain.service.PermissaoService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/v1/permissoes")
public class PermissaoController implements PermissaoControllerOpenApi {

    private PermissaoService service;
    private PermissaoAssembler assembler;
    private ApiLinks links;


    @GetMapping
    public CollectionModel<PermissaoModel> listar() {
        return assembler.toCollectionModel(service.listar());
    }
}
