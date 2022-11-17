package com.gabriel.algafood.api.assembler;

import com.gabriel.algafood.api.controller.UsuarioController;
import com.gabriel.algafood.api.controller.UsuarioGrupoController;
import com.gabriel.algafood.api.model.UsuarioModel;
import com.gabriel.algafood.api.model.request.UsuarioRequest;
import com.gabriel.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Component
public class UsuarioAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioAssembler() {
        super(UsuarioController.class, UsuarioModel.class);
    }

    @Override
    public UsuarioModel toModel(Usuario usuario) {
        var usuarioModel = createModelWithId(usuario.getId(), usuario);
        modelMapper.map(usuario, usuarioModel);

        usuarioModel.add(linkTo(methodOn(UsuarioController.class).listar()).withRel("usuarios"));
        usuarioModel.add(linkTo(methodOn(UsuarioGrupoController.class).listarGrupos(usuario.getId())).withRel("grupos-usuarios"));

        return usuarioModel;
    }

    public Usuario toEntity(UsuarioRequest request) {
        return modelMapper.map(request, Usuario.class);
    }

    @Override
    public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(methodOn(UsuarioController.class)).withSelfRel());
    }

    //    public List<UsuarioModel> toCollectionModel(Collection<Usuario> usuarios) {
//        return usuarios.stream()
//                .map(usuario -> toModel(usuario))
//                .collect(Collectors.toList());
//    }

    public void copyToEntity(UsuarioRequest request, Usuario usuario) {
        modelMapper.map(request, usuario);
    }
}
