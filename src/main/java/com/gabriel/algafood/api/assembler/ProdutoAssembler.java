package com.gabriel.algafood.api.assembler;

import com.gabriel.algafood.api.ApiLinks;
import com.gabriel.algafood.api.controller.RestauranteProdutoController;
import com.gabriel.algafood.api.model.ProdutoModel;
import com.gabriel.algafood.api.model.request.ProdutoRequest;
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

    public ProdutoAssembler() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }

    @Override
    public ProdutoModel toModel(Produto produto) {
        var restauranteId = produto.getRestaurante().getId();
        var produtoModel = createModelWithId(produto.getId(), produto, restauranteId);
        modelMapper.map(produto, produtoModel);

        produtoModel.add(links.linkToProdutos(restauranteId, "produtos"));
        produtoModel.add(links.linkToFotoProduto(restauranteId, produto.getId(), "foto"));
        return produtoModel;
    }

    public Produto toEntity(ProdutoRequest request) {
        return modelMapper.map(request, Produto.class);
    }

    public void copyToEntity(ProdutoRequest request, Produto produto) {
        modelMapper.map(request, produto);
    }

}
