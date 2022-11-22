package com.gabriel.algafood.api.v2;

import com.gabriel.algafood.api.v2.controller.CidadeControllerV2;
import com.gabriel.algafood.api.v2.controller.CozinhaControllerV2;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class ApiLinksV2 {

    private static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    public Link linkToCidades(String rel) {
        return linkTo(CidadeControllerV2.class).withRel(rel);
    }

    public Link linkToCidades() {
        return linkToCidades(IanaLinkRelations.SELF_VALUE);
    }

    public Link linkToCozinhas(String rel) {
        var cozinhasUrl = linkTo(CozinhaControllerV2.class).toUri().toString();
        return Link.of(UriTemplate.of(cozinhasUrl, PAGINACAO_VARIABLES), rel);
    }

    public Link linkToCozinhas() {
        return linkToCozinhas(IanaLinkRelations.SELF_VALUE);
    }

}
