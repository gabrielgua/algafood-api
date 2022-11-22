package com.gabriel.algafood.api.v2.assembler;

import com.gabriel.algafood.api.v2.ApiLinksV2;
import com.gabriel.algafood.api.v2.controller.CidadeControllerV2;
import com.gabriel.algafood.api.v2.model.CidadeModelV2;
import com.gabriel.algafood.api.v2.model.request.CidadeRequestV2;
import com.gabriel.algafood.domain.model.Cidade;
import com.gabriel.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeModelV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinksV2 linksV2;

    public CidadeAssemblerV2() {
        super(CidadeControllerV2.class, CidadeModelV2.class);
    }

    @Override
    public CidadeModelV2 toModel(Cidade cidade) {
        var cidadeModelV2 = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeModelV2);
        cidadeModelV2.add(linksV2.linkToCidades("cidades"));
        return cidadeModelV2;
    }

    @Override
    public CollectionModel<CidadeModelV2> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(linksV2.linkToCidades());
    }

    public Cidade toEntity(CidadeRequestV2 requestV2) {
        return modelMapper.map(requestV2, Cidade.class);
    }

    public void copyToEntity(CidadeRequestV2 requestV2, Cidade cidade) {
        cidade.setEstado(new Estado());
        modelMapper.map(requestV2, cidade);
    }
}
