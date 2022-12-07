package com.gabriel.algafood.api.v1.assembler;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.controller.CidadeController;
import com.gabriel.algafood.api.v1.model.CidadeModel;
import com.gabriel.algafood.api.v1.model.request.CidadeRequest;
import com.gabriel.algafood.core.security.SecurityConfig;
import com.gabriel.algafood.domain.model.Cidade;
import com.gabriel.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CidadeAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    @Autowired
    private SecurityConfig securityConfig;

    public CidadeAssembler() {
        super(CidadeController.class, CidadeModel.class);
    }

    @Override
    public CidadeModel toModel(Cidade cidade) {

        var cidadeModel = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeModel);

        if (securityConfig.podeConsultarCidades()) {
            cidadeModel.add(links.linkToCidades("cidades"));
            cidadeModel.getEstado().add(links.linkToEstado(cidade.getEstado().getId()));
        }

        if (securityConfig.podeConsultarEstados()) {
            cidadeModel.getEstado().add(links.linkToEstados("estados"));
        }

        return cidadeModel;
    }

    public Cidade toEntity(CidadeRequest request) {
        return modelMapper.map(request, Cidade.class);
    }

    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
        var model = super.toCollectionModel(entities);
        if (securityConfig.podeConsultarCidades()) {
            model.add(links.linkToCidades());
        }
        return model;
    }

    public void copyToEntity (CidadeRequest request, Cidade cidade) {
        // Para evitar Exception do JPA org.hibernate.HibernateException: Tried to change the Identifier of an instance...
        cidade.setEstado(new Estado());
        modelMapper.map(request, cidade);
    }
}
