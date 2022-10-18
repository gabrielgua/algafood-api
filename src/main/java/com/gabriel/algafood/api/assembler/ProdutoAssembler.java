package com.gabriel.algafood.api.assembler;

import com.gabriel.algafood.api.model.ProdutoModel;
import com.gabriel.algafood.api.model.request.ProdutoRequest;
import com.gabriel.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoModel toModel(Produto produto) {
        return modelMapper.map(produto, ProdutoModel.class);
    }

    public Produto toEntity(ProdutoRequest request) {
        return modelMapper.map(request, Produto.class);
    }

    public List<ProdutoModel> toCollectionModel(List<Produto> produtos) {
        return produtos.stream()
                .map(produto -> toModel(produto))
                .collect(Collectors.toList());
    }

    public void copyToEntity(ProdutoRequest request, Produto produto) {
        modelMapper.map(request, produto);
    }

}
