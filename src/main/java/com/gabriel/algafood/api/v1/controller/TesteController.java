package com.gabriel.algafood.api.v1.controller;

import com.gabriel.algafood.api.v1.assembler.CozinhaAssembler;
import com.gabriel.algafood.api.v1.assembler.RestauranteAssembler;
import com.gabriel.algafood.api.v1.model.CozinhaModel;
import com.gabriel.algafood.api.v1.model.RestauranteModel;
import com.gabriel.algafood.domain.model.Restaurante;
import com.gabriel.algafood.domain.repository.CozinhaRepository;
import com.gabriel.algafood.domain.repository.RestauranteRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import static com.gabriel.algafood.infrastructure.spec.RestauranteSpecs.comFreteGratis;
import static com.gabriel.algafood.infrastructure.spec.RestauranteSpecs.comNomeSemelhante;

@RestController
@AllArgsConstructor
@ApiIgnore
public class TesteController {

    private CozinhaRepository cozinhaRepository;
    private CozinhaAssembler cozinhaAssembler;
    private RestauranteAssembler restauranteAssembler;
    private RestauranteRepository restauranteRepository;

    @GetMapping("restaurantes/com-frete-gratis-e-nome-semelhante")
    public List<Restaurante> restaurantesComFreteGratis(String nome) {
        return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
    }

    @GetMapping("/restaurantes/primeiro")
    public RestauranteModel buscarPrimeiroRestaurante() {
        return restauranteAssembler.toModel(restauranteRepository.buscarPrimeiro());
    }

    @GetMapping("/cozinhas/primeiro")
    public CozinhaModel buscarPrimeiraCozinha() {
        var cozinha = cozinhaRepository.buscarPrimeiro();
        return cozinhaAssembler.toModel(cozinha);
    }
}
