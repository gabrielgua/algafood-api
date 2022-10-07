package com.gabriel.algafood;

import com.gabriel.algafood.domain.model.Cozinha;
import com.gabriel.algafood.domain.model.Restaurante;
import com.gabriel.algafood.domain.repository.CozinhaRepository;
import com.gabriel.algafood.domain.repository.RestauranteRepository;
import com.gabriel.algafood.util.DatabaseCleaner;
import com.gabriel.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@TestPropertySource("/application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroRestauranteIT {

    private static final String VIOLACAO_DA_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";
    private static final String DADOS_INVALIDOS_PROBLEM_TYPE = "Dados inválidos";

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private String jsonRestauranteCorreto;
    private String jsonRestauranteSemFrete;
    private String jsonRestauranteSemCozinha;
    private String jsonRestauranteComCozinhaInexistente;
    private Restaurante burgerTopRestaurante;
    private int restauranteSalvos;

    @BeforeEach
    private void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = " /restaurantes";

        jsonRestauranteCorreto = ResourceUtils.getContentFromResource("/json/correto/restaurante-new-york-barbecue.json");
        jsonRestauranteSemFrete = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-new-york-barbecue-sem-frete.json");
        jsonRestauranteSemCozinha = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-new-york-barbecue-sem-cozinha.json");
        jsonRestauranteComCozinhaInexistente = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-new-york-barbecue-com-cozinha-inexistente.json");

        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    public void deveRetornarStatus200_quandoConsultarRestaurantes () {
        RestAssured
                .given().accept(ContentType.JSON)
                .when().get()
                .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarStatus201_quandoCadastrarRestauranteCorreto() {
        RestAssured
                .given()
                    .body(jsonRestauranteCorreto)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when().post()
                .then().statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarStatus400_quandoCadastrarRestauranteSemFrete() {
        RestAssured
                .given()
                    .body(jsonRestauranteSemFrete)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when().post()
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("title", Matchers.equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
    }

    @Test
    public void deveRetornarStatus400_quandoCadastrarRestauranteSemCozinha() {
        RestAssured
                .given()
                    .body(jsonRestauranteSemCozinha)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when().post()
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("title", Matchers.equalTo(DADOS_INVALIDOS_PROBLEM_TYPE));
    }

    @Test
    public void deveRetornarStatus400_quandoCadastrarRestauranteComCozinhaInexistente() {
        RestAssured
                .given()
                    .body(jsonRestauranteComCozinhaInexistente)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when().post()
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("title", Matchers.equalTo(VIOLACAO_DA_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
    }

    @Test
    public void deveRetornarStatusECorpoCorreto_quandoConsultarRestauranteExistente() {
        RestAssured
                .given()
                    .pathParam("id", burgerTopRestaurante.getId())
                    .accept(ContentType.JSON)
                .when().get("/{id}")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("nome", Matchers.equalTo(burgerTopRestaurante.getNome()));
    }

    @Test
    public void deveRetornarStatus404_quandoConsultarRestauranteInexistente() {
        RestAssured
                .given()
                    .pathParam("id", restauranteSalvos + 1)
                    .accept(ContentType.JSON)
                .when().get("/{id}")
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }
    private void prepararDados() {
        List<Cozinha> cozinhas = new ArrayList<>();
        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");

        Cozinha cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");

        cozinhas.add(cozinhaBrasileira);
        cozinhas.add(cozinhaAmericana);
        cozinhaRepository.saveAll(cozinhas);

        List<Restaurante> restaurantes = new ArrayList<>();
        burgerTopRestaurante = new Restaurante();
        burgerTopRestaurante.setNome("Burger Top");
        burgerTopRestaurante.setTaxaFrete(new BigDecimal(10));
        burgerTopRestaurante.setCozinha(cozinhaAmericana);

        Restaurante comidaMineira = new Restaurante();
        comidaMineira.setNome("Comida Mineira");
        comidaMineira.setTaxaFrete(new BigDecimal(5));
        comidaMineira.setCozinha(cozinhaBrasileira);

        restaurantes.add(burgerTopRestaurante);
        restaurantes.add(comidaMineira);
        restauranteRepository.saveAll(restaurantes);

        restauranteSalvos = restaurantes.size();
    }

}
