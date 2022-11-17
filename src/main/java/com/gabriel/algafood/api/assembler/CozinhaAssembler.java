package com.gabriel.algafood.api.assembler;

import com.gabriel.algafood.api.controller.CozinhaController;
import com.gabriel.algafood.api.model.CozinhaModel;
import com.gabriel.algafood.api.model.request.CozinhaRequest;
import com.gabriel.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CozinhaAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaAssembler() {
        super(CozinhaController.class, CozinhaModel.class);
    }

    @Override
    public CozinhaModel toModel(Cozinha cozinha) {
        var cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);

        cozinhaModel.add(linkTo(CozinhaController.class).withRel("cozinhas"));


        return cozinhaModel;
    }

//    public List<CozinhaModel> toCollectionModel(List<Cozinha> cozinhas) {
//        return cozinhas.stream()
//                .map(cozinha -> toModel(cozinha))
//                .collect(Collectors.toList());
//    }

    public Cozinha toEntity(CozinhaRequest request) {
        return modelMapper.map(request, Cozinha.class);
    }

    public void copyToEntity (CozinhaRequest request, Cozinha cozinha) {
        modelMapper.map(request, cozinha);
    }
}
