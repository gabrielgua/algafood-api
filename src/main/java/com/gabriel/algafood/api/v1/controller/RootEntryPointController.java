package com.gabriel.algafood.api.v1.controller;

import com.gabriel.algafood.api.v1.ApiLinks;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@AllArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@ApiIgnore
public class RootEntryPointController {

    private ApiLinks links;

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        rootEntryPointModel.add(links.linkToCozinhas("cozinhas"));
        rootEntryPointModel.add(links.linkToRestaurantes("restaurantes"));
        rootEntryPointModel.add(links.linkToPedidos("pedidos"));
        rootEntryPointModel.add(links.linkToUsuarios("usuarios"));
        rootEntryPointModel.add(links.linkToGrupos("grupos"));
        rootEntryPointModel.add(links.linkToPermissoes("permissoes"));
        rootEntryPointModel.add(links.linkToFormaPagamentos("formasPagamento"));
        rootEntryPointModel.add(links.linkToCidades("cidades"));
        rootEntryPointModel.add(links.linkToEstados("estados"));
        rootEntryPointModel.add(links.linkToEstatisticas("estatisticas"));


        return rootEntryPointModel;
    }


    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {

    }

}
