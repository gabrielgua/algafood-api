package com.gabriel.algafood.api.assembler;

import com.gabriel.algafood.api.ApiLinks;
import com.gabriel.algafood.api.controller.RestauranteController;
import com.gabriel.algafood.api.model.RestauranteModel;
import com.gabriel.algafood.api.model.request.RestauranteRequest;
import com.gabriel.algafood.domain.model.Cidade;
import com.gabriel.algafood.domain.model.Cozinha;
import com.gabriel.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    public RestauranteAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        var restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);

        var cozinhaId = restaurante.getCozinha().getId();
        var cidadeId = restaurante.getEndereco().getCidade().getId();

        restauranteModel.add(links.linkToRestaurantes("restaurantes"));
        restauranteModel.getCozinha().add(links.linkToCozinha(cozinhaId));

        if (restauranteModel.getEndereco() != null && restauranteModel.getEndereco().getCidade() != null) {
            restauranteModel.getEndereco().getCidade().add(links.linkToCidade(cidadeId));
        }

        restauranteModel.add(links.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));
        restauranteModel.add(links.linkToResponsaveisRestaurante(restaurante.getId(), "responsaveis"));
        restauranteModel.add(links.linkToProdutos(restaurante.getId(), "produtos"));

        if (restaurante.ativacaoPermitida()) {
            restauranteModel.add(links.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
        }
        if (restaurante.inativacaoPermitida()) {
            restauranteModel.add(links.linkToRestauranteFechamento(restaurante.getId(), "inativar"));
        }
        if (restaurante.aberturaPermitida()) {
            restauranteModel.add(links.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
        }
        if (restaurante.fechamentoPermitido()) {
            restauranteModel.add(links.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
        }
        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(links.linkToRestaurantes());
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
