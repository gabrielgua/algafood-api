package com.gabriel.algafood.api.v1.controller;

import com.gabriel.algafood.domain.model.Cozinha;
import com.gabriel.algafood.domain.model.Restaurante;
import com.gabriel.algafood.domain.repository.CozinhaRepository;
import com.gabriel.algafood.domain.repository.RestauranteRepository;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

import static com.gabriel.algafood.infrastructure.spec.RestauranteSpecs.comFreteGratis;
import static com.gabriel.algafood.infrastructure.spec.RestauranteSpecs.comNomeSemelhante;

@RestController
@AllArgsConstructor
@ApiIgnore
public class TesteController {

    private CozinhaRepository cozinhaRepository;
    private RestauranteRepository restauranteRepository;

    @GetMapping("restaurantes/com-frete-gratis-e-nome-semelhante")
    public List<Restaurante> restaurantesComFreteGratis(String nome) {
        return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
    }

    @GetMapping("/restaurantes/primeiro")
    public Optional<Restaurante> buscarPrimeiroRestaurante() {
        return restauranteRepository.buscarPrimeiro();
    }

    @GetMapping("/cozinhas/primeiro")
    public Optional<Cozinha> buscarPrimeiraCozinha() {
        return cozinhaRepository.buscarPrimeiro();
    }


}
