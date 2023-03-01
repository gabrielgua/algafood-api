package com.gabriel.algafood.api.v1.assembler;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.controller.RestauranteProdutoController;
import com.gabriel.algafood.api.v1.model.ProdutoModel;
import com.gabriel.algafood.api.v1.model.request.ProdutoRequest;
import com.gabriel.algafood.core.security.SecurityConfig;
import com.gabriel.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    @Autowired
    private SecurityConfig securityConfig;

    public ProdutoAssembler() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }

    @Override
    public ProdutoModel toModel(Produto produto) {
        var restauranteId = produto.getRestaurante().getId();
        var produtoModel = createModelWithId(produto.getId(), produto, restauranteId);
        modelMapper.map(produto, produtoModel);

        if (securityConfig.podeConsultarRestaurantes()) {
            produtoModel.add(links.linkToProdutos(restauranteId, "produtos"));
            produtoModel.add(links.linkToFotoProduto(restauranteId, produto.getId(), "foto"));
        }

        return produtoModel;
    }

    public Produto toEntity(ProdutoRequest request) {
        return modelMapper.map(request, Produto.class);
    }

    public void copyToEntity(ProdutoRequest request, Produto produto) {
        modelMapper.map(request, produto);
    }

}
