package com.gabriel.algafood.api.assembler;

import com.gabriel.algafood.api.ApiLinks;
import com.gabriel.algafood.api.controller.UsuarioController;
import com.gabriel.algafood.api.model.UsuarioModel;
import com.gabriel.algafood.api.model.request.UsuarioRequest;
import com.gabriel.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;


@Component
public class UsuarioAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    public UsuarioAssembler() {
        super(UsuarioController.class, UsuarioModel.class);
    }

    @Override
    public UsuarioModel toModel(Usuario usuario) {
        var usuarioModel = createModelWithId(usuario.getId(), usuario);
        modelMapper.map(usuario, usuarioModel);

        usuarioModel.add(links.linkToUsuarios("usuarios"));
        usuarioModel.add(links.linkToGruposUsuario(usuario.getId(), "grupos-usuarios"));

        return usuarioModel;
    }

    public Usuario toEntity(UsuarioRequest request) {
        return modelMapper.map(request, Usuario.class);
    }

    @Override
    public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
                .add(links.linkToUsuarios());
    }

    public void copyToEntity(UsuarioRequest request, Usuario usuario) {
        modelMapper.map(request, usuario);
    }
}
