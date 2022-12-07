package com.gabriel.algafood.api.v1.assembler;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.controller.UsuarioController;
import com.gabriel.algafood.api.v1.model.UsuarioModel;
import com.gabriel.algafood.api.v1.model.request.UsuarioRequest;
import com.gabriel.algafood.core.security.SecurityConfig;
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

    @Autowired
    private SecurityConfig securityConfig;

    public UsuarioAssembler() {
        super(UsuarioController.class, UsuarioModel.class);
    }

    @Override
    public UsuarioModel toModel(Usuario usuario) {
        var usuarioModel = createModelWithId(usuario.getId(), usuario);
        modelMapper.map(usuario, usuarioModel);

        if (securityConfig.podeConsultarUsuariosGruposPermissoes()) {
            usuarioModel.add(links.linkToUsuarios("usuarios"));
            usuarioModel.add(links.linkToGruposUsuario(usuario.getId(), "grupos-usuarios"));
        }

        return usuarioModel;
    }

    public Usuario toEntity(UsuarioRequest request) {
        return modelMapper.map(request, Usuario.class);
    }

    @Override
    public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
        var model = super.toCollectionModel(entities);

        if (securityConfig.podeConsultarUsuariosGruposPermissoes()) {
            model.add(links.linkToUsuarios("usuarios"));
        }

        return model;
    }

    public void copyToEntity(UsuarioRequest request, Usuario usuario) {
        modelMapper.map(request, usuario);
    }
}
