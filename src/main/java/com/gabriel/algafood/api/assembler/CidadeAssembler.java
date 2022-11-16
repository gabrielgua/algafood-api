package com.gabriel.algafood.api.assembler;

import com.gabriel.algafood.api.controller.CidadeController;
import com.gabriel.algafood.api.controller.EstadoController;
import com.gabriel.algafood.api.model.CidadeModel;
import com.gabriel.algafood.api.model.request.CidadeRequest;
import com.gabriel.algafood.domain.model.Cidade;
import com.gabriel.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CidadeAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeAssembler() {
        super(CidadeController.class, CidadeModel.class);
    }

    @Override
    public CidadeModel toModel(Cidade cidade) {

        var cidadeModel = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeModel);

        cidadeModel.add(linkTo(methodOn(CidadeController.class).listar()).withRel("cidades"));
        cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class).buscarPorId(cidadeModel.getEstado().getId())).withSelfRel());
        cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class).listar()).withRel("estados"));

        return cidadeModel;
    }

    public Cidade toEntity(CidadeRequest request) {
        return modelMapper.map(request, Cidade.class);
    }

    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {

        return super.toCollectionModel(entities)
                .add(linkTo(CidadeController.class).withSelfRel());
    }

    //    public List<CidadeModel> toCollectionModel(List<Cidade> cidades) {
//        return cidades.stream()
//                .map(cidade -> toModel(cidade))
//                .collect(Collectors.toList());
//    }

    public void copyToEntity (CidadeRequest request, Cidade cidade) {
        // Para evitar Exception do JPA org.hibernate.HibernateException: Tried to change the Identifier of an instance...
        cidade.setEstado(new Estado());
        modelMapper.map(request, cidade);
    }
}
