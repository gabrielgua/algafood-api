package com.gabriel.algafood.api.v1.assembler;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.controller.CozinhaController;
import com.gabriel.algafood.api.v1.model.CozinhaModel;
import com.gabriel.algafood.api.v1.model.request.CozinhaRequest;
import com.gabriel.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    public CozinhaAssembler() {
        super(CozinhaController.class, CozinhaModel.class);
    }

    @Override
    public CozinhaModel toModel(Cozinha cozinha) {
        var cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);

        cozinhaModel.add(links.linkToCozinhas("cozinhas"));


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
