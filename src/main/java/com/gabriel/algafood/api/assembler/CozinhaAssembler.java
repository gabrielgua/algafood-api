package com.gabriel.algafood.api.assembler;

import com.gabriel.algafood.api.model.CozinhaModel;
import com.gabriel.algafood.api.model.request.CozinhaRequest;
import com.gabriel.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaModel toModel(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaModel.class);
    }

    public List<CozinhaModel> toCollectionModel(List<Cozinha> cozinhas) {
        return cozinhas.stream()
                .map(cozinha -> toModel(cozinha))
                .collect(Collectors.toList());
    }

    public Cozinha toEntity(CozinhaRequest request) {
        return modelMapper.map(request, Cozinha.class);
    }
}
