package com.gabriel.algafood.api.v2.assembler;

import com.gabriel.algafood.api.v2.ApiLinksV2;
import com.gabriel.algafood.api.v2.controller.CozinhaControllerV2;
import com.gabriel.algafood.api.v2.model.CozinhaModelV2;
import com.gabriel.algafood.api.v2.model.request.CozinhaRequestV2;
import com.gabriel.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaAssemblerV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModelV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinksV2 linksV2;

    public CozinhaAssemblerV2() {
        super(CozinhaControllerV2.class, CozinhaModelV2.class);
    }

    @Override
    public CozinhaModelV2 toModel(Cozinha cozinha) {
        var cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);

        cozinhaModel.add(linksV2.linkToCozinhas("cozinhas"));
        return cozinhaModel;
    }

    @Override
    public CollectionModel<CozinhaModelV2> toCollectionModel(Iterable<? extends Cozinha> entities) {
        return super.toCollectionModel(entities)
                .add(linksV2.linkToCozinhas());
    }

    public Cozinha toEntity(CozinhaRequestV2 requestV2) {
        return modelMapper.map(requestV2, Cozinha.class);
    }

    public void copyToEntity(CozinhaRequestV2 requestV2, Cozinha cozinha) {
        modelMapper.map(requestV2, cozinha);
    }
}
