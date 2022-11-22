package com.gabriel.algafood.api.v2.controller;

import com.gabriel.algafood.api.v2.ApiLinksV2;
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
@RequestMapping(path = "v2", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointControllerV2 {


    private ApiLinksV2 linksV2;

    @GetMapping
    public RootEntryPointModelV2 rootV2() {
        var rootEntryPointModelV2 = new RootEntryPointModelV2();

        rootEntryPointModelV2.add(linksV2.linkToCidades("cidades"));
        rootEntryPointModelV2.add(linksV2.linkToCozinhas("cozinhas"));

        return rootEntryPointModelV2;
    }

    private static class RootEntryPointModelV2 extends RepresentationModel<RootEntryPointModelV2> {

    }
}
