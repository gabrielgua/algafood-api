package com.gabriel.algafood;

import com.gabriel.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.gabriel.algafood.domain.exception.EntidadeEmUsoException;
import com.gabriel.algafood.domain.model.Cozinha;
import com.gabriel.algafood.domain.model.Restaurante;
import com.gabriel.algafood.domain.repository.CozinhaRepository;
import com.gabriel.algafood.domain.repository.RestauranteRepository;
import com.gabriel.algafood.domain.service.CozinhaService;
import com.gabriel.algafood.util.DatabaseCleaner;
import com.gabriel.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

@ExtendWith(SpringExtension.class)
@TestPropertySource("/application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaIT {

    @Autowired
    private CozinhaService cozinhaService;
    @Autowired
    private DatabaseCleaner databaseCleaner;
    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private RestauranteRepository restauranteRepository;

    private int cozinhasSalvas;
    private Cozinha cozinhaAmericana;
    private String jsonCozinhaChinesaCorreta;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        jsonCozinhaChinesaCorreta = ResourceUtils.getContentFromResource("/json/correto/cozinha-chinesa-correta.json");
        databaseCleaner.clearTables();
        prepararDados();
    }

    private void prepararDados() {
        List<Cozinha> cozinhas = new ArrayList<>();
        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Tailandesa");

        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Americana");

        Restaurante restaurante = new Restaurante();
        restaurante.setNome("Comida Tailandesa");
        restaurante.setTaxaFrete(BigDecimal.TEN);
        restaurante.setCozinha(cozinha1);

        cozinhas.add(cozinha1);
        cozinhas.add(cozinha2);

        this.cozinhaAmericana = cozinha2;
        this.cozinhasSalvas = cozinhas.size();
        cozinhaRepository.saveAll(cozinhas);
        restauranteRepository.save(restaurante);
    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_quandoConsultarCozinhaExistente() {
        RestAssured
                .given()
                    .pathParam("id", cozinhaAmericana.getId())
                    .accept(ContentType.JSON)
                .when().get("/{id}")
                .then().body("nome", Matchers.equalTo(cozinhaAmericana.getNome()));
    }

    @Test
    public void deveRetornarStatus404_quandoConsultarCozinhaInexistente() {
        RestAssured
                .given()
                    .pathParam("id", cozinhasSalvas + 1)
                    .accept(ContentType.JSON)
                .when().get("/{id}")
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    //Testes de API REST
    @Test
    public void deveRetornarStatus200_quandoConsultarCozinhas() {
        RestAssured
                .given().accept(ContentType.JSON)
                .when().get()
                .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveConterAQuantiaCertaDeCozinhas_quandoConsultarCozinhas() {
        RestAssured
                .given().accept(ContentType.JSON)
                .when().get()
                .then().body("", Matchers.hasSize(cozinhasSalvas));
    }

    @Test
    public void deveRetornarStatus201_quandoCadastrarCozinhaComSucesso() {
        RestAssured.
                given()
                    .body(jsonCozinhaChinesaCorreta)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when().post()
                .then().statusCode(HttpStatus.CREATED.value());
    }

    //Integração
    @Test
    public void deveAtribuirId_quandoCadastrarCozinhaComDadosCorretos() {
        Cozinha cozinhaTeste = new Cozinha();
        cozinhaTeste.setNome("Chinesa");

        cozinhaTeste = cozinhaService.salvar(cozinhaTeste);

        assertThat(cozinhaTeste).isNotNull();
        assertThat(cozinhaTeste.getId()).isNotNull();
    }

    @Test
    public void deveFalhar_quandoCadastrarCozinhaSemNome() {
        Cozinha cozinhaTeste = new Cozinha();
        cozinhaTeste.setNome(null);

        ConstraintViolationException erroEsperado = Assertions.assertThrows(
                ConstraintViolationException.class, () -> {
                    cozinhaService.salvar(cozinhaTeste);
                });

        assertThatExceptionOfType(erroEsperado.getClass());
    }

    @Test
    public void deveFalhar_quandoCozinhaEmUso() {
        EntidadeEmUsoException erroEsperado = Assertions.assertThrows(
                EntidadeEmUsoException.class, () -> {
                    cozinhaService.remover(1L);
                });

        assertThatExceptionOfType(erroEsperado.getClass());
    }

    @Test
    public void deveFalhar_quandoRemoverCozinhaInexistente() {
        Long cozinhasCadastradas = cozinhaService.listar().stream().count();

        CozinhaNaoEncontradaException erroEsperado = Assertions.assertThrows(
                CozinhaNaoEncontradaException.class, () -> {
                    cozinhaService.remover(cozinhasCadastradas + 1L);
                });

        assertThatExceptionOfType(erroEsperado.getClass());
    }
}
