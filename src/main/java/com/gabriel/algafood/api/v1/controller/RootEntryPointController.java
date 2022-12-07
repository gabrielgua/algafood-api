package com.gabriel.algafood.api.v1.controller;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.core.security.SecurityConfig;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
@ApiIgnore
@RestController
@AllArgsConstructor
@RequestMapping(path = "v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    private ApiLinks links;
    private SecurityConfig securityConfig;

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        if (securityConfig.podeConsultarCozinhas()) {
            rootEntryPointModel.add(links.linkToCozinhas("cozinhas"));
        }

        if (securityConfig.podeConsultarRestaurantes()) {
            rootEntryPointModel.add(links.linkToRestaurantes("restaurantes"));
        }

        if (securityConfig.podeBuscarPedidos()) {
            rootEntryPointModel.add(links.linkToPedidos("pedidos"));
        }

        if (securityConfig.podeConsultarUsuariosGruposPermissoes()) {
            rootEntryPointModel.add(links.linkToUsuarios("usuarios"));
            rootEntryPointModel.add(links.linkToGrupos("grupos"));
            rootEntryPointModel.add(links.linkToPermissoes("permissoes"));
        }

        if (securityConfig.podeConsultarFormasDePagamento()) {
            rootEntryPointModel.add(links.linkToFormaPagamentos("formasPagamento"));
        }

        if (securityConfig.podeConsultarCidades()) {
            rootEntryPointModel.add(links.linkToCidades("cidades"));
        }

        if (securityConfig.podeConsultarEstados()) {
            rootEntryPointModel.add(links.linkToEstados("estados"));
        }

        if (securityConfig.podeConsultarEstatisticas()) {
            rootEntryPointModel.add(links.linkToEstatisticas("estatisticas"));
        }

        return rootEntryPointModel;
    }


    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }

}
