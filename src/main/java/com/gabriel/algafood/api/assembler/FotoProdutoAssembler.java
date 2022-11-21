package com.gabriel.algafood.api.assembler;

import com.gabriel.algafood.api.ApiLinks;
import com.gabriel.algafood.api.controller.RestauranteProdutoFotoController;
import com.gabriel.algafood.api.model.FotoProdutoModel;
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

    public FotoProdutoAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
    }

    @Override
    public FotoProdutoModel toModel(FotoProduto foto) {
        var restauranteId = foto.getRestauranteId();
        var produtoId = foto.getProduto().getId();

        return modelMapper.map(foto, FotoProdutoModel.class)
                .add(links.linkToFotoProduto(restauranteId, produtoId))
                .add(links.linkToProduto(restauranteId, produtoId, "produto"));
    }
}
