package com.gabriel.algafood.api.v1.assembler;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.controller.RestauranteProdutoFotoController;
import com.gabriel.algafood.api.v1.model.FotoProdutoModel;
import com.gabriel.algafood.core.security.SecurityConfig;
import com.gabriel.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    @Autowired
    private SecurityConfig securityConfig;


    public FotoProdutoAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
    }

    @Override
    public FotoProdutoModel toModel(FotoProduto foto) {
        var restauranteId = foto.getRestauranteId();
        var produtoId = foto.getProduto().getId();
        var fotoProdutoModel = modelMapper.map(foto, FotoProdutoModel.class);
        if (securityConfig.podeConsultarRestaurantes()) {
            fotoProdutoModel.add(links.linkToFotoProduto(restauranteId, produtoId))
                    .add(links.linkToProduto(restauranteId, produtoId, "produto"));
        }
        return fotoProdutoModel;
    }
}
