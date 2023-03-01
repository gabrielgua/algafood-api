package com.gabriel.algafood.api.v1.assembler;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.controller.RestauranteController;
import com.gabriel.algafood.api.v1.model.RestauranteModel;
import com.gabriel.algafood.api.v1.model.request.RestauranteRequest;
import com.gabriel.algafood.core.security.SecurityConfig;
import com.gabriel.algafood.domain.model.Cidade;
import com.gabriel.algafood.domain.model.Cozinha;
import com.gabriel.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    @Autowired
    private SecurityConfig securityConfig;

    public RestauranteAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        var restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);

        var cozinhaId = restaurante.getCozinha().getId();

        if (securityConfig.podeConsultarRestaurantes()) {
            restauranteModel.add(links.linkToRestaurantes("restaurantes"));
            restauranteModel.add(links.linkToProdutos(restaurante.getId(), "produtos"));
            restauranteModel.add(links.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));
            restauranteModel.getCozinha().add(links.linkToCozinha(cozinhaId));

            if (restauranteModel.getEndereco() != null && restauranteModel.getEndereco().getCidade() != null) {
                var cidadeId = restaurante.getEndereco().getCidade().getId();
                restauranteModel.getEndereco().getCidade().add(links.linkToCidade(cidadeId));
            }
        }

        if (securityConfig.podeGerenciarCadastroRestaurantes()) {
            restauranteModel.add(links.linkToResponsaveisRestaurante(restaurante.getId(), "responsaveis"));

            if (restaurante.ativacaoPermitida()) {
                restauranteModel.add(links.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
            }
            if (restaurante.inativacaoPermitida()) {
                restauranteModel.add(links.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
            }
        }

        if (securityConfig.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
            if (restaurante.aberturaPermitida()) {
                restauranteModel.add(links.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
            }
            if (restaurante.fechamentoPermitido()) {
                restauranteModel.add(links.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
            }
        }

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        var collectionModel = super.toCollectionModel(entities);

        if (securityConfig.podeConsultarRestaurantes()) {
            collectionModel.add(links.linkToRestaurantes("restaurantes"));
        }

        return collectionModel;
    }

    public Restaurante toEntity(RestauranteRequest request) {
        return modelMapper.map(request, Restaurante.class);
    }

    public void copyToEntity(RestauranteRequest request, Restaurante restaurante) {
        // Para evitar Exception do JPA org.hibernate.HibernateException: Tried to change the Identifier of an instance...
        restaurante.setCozinha(new Cozinha());

        if (restaurante.getEndereco() != null) {
            restaurante.getEndereco().setCidade(new Cidade());
        }

        modelMapper.map(request, restaurante);
    }

}
