package com.gabriel.algafood.core.openapi;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gabriel.algafood.api.exceptionhandler.Problem;
import com.gabriel.algafood.api.model.*;
import com.gabriel.algafood.api.openapi.model.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

    @Bean
    public Docket apiDocket() {
        var typeResolver = new TypeResolver();

        return new Docket(DocumentationType.OAS_30)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.gabriel.algafood.api"))
                    .paths(PathSelectors.any())
//                   .paths(PathSelectors.ant("/restaurantes/*"), PathSelectors.ant("/cidades/**"))
                    .build()
                .useDefaultResponseMessages(false)
//                .globalRequestParameters(Collections.singletonList(
//                        new RequestParameterBuilder()
//                                .name("campos")
//                                .description("Nomes das propriedades para filtrar na resposta, separados por vírgula")
//                                .in(ParameterType.QUERY)
//                                .required(true)
//                                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//                                .build()
//                ))
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .ignoredParameterTypes(ServletWebRequest.class)
                .additionalModels(typeResolver.resolve(Problem.class))
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .alternateTypeRules(
                        AlternateTypeRules.newRule(
                                typeResolver.resolve(PagedModel.class, CozinhaModel.class),
                                CozinhasModelOpenApi.class),
                        AlternateTypeRules.newRule(
                                typeResolver.resolve(CollectionModel.class, CidadeModel.class),
                                CidadesModelOpenApi.class),
                        AlternateTypeRules.newRule(
                                typeResolver.resolve(CollectionModel.class, EstadoModel.class),
                                EstadosModelOpenApi.class),
                        AlternateTypeRules.newRule(
                                typeResolver.resolve(CollectionModel.class, FormaPagamentoModel.class),
                                FormasPagamentoModelOpenApi.class),
                        AlternateTypeRules.newRule(
                                typeResolver.resolve(CollectionModel.class, GrupoModel.class),
                                GruposModelOpenApi.class),
                        AlternateTypeRules.newRule(
                                typeResolver.resolve(CollectionModel.class, PermissaoModel.class),
                                PermissoesModelOpenApi.class),
                        AlternateTypeRules.newRule(
                                typeResolver.resolve(PagedModel.class, PedidoResumoModel.class),
                                PedidosResumoModelOpenApi.class),
                        AlternateTypeRules.newRule(
                                typeResolver.resolve(CollectionModel.class, ProdutoModel.class),
                                ProdutosModelOpenApi.class),
                        AlternateTypeRules.newRule(
                                typeResolver.resolve(CollectionModel.class, RestauranteBasicoModel.class),
                                RestaurantesBasicoModelOpenApi.class),
                        AlternateTypeRules.newRule(
                                typeResolver.resolve(CollectionModel.class, UsuarioModel.class),
                                UsuariosModelOpenApi.class)
                )
                .apiInfo(apiInfo())
                .tags(
                        new Tag("Cidades", "Gerencia as Cidades"),
                        new Tag("Grupos", "Gerencia os Grupos"),
                        new Tag("Cozinhas", "Gerencia as Cozinhas"),
                        new Tag("Formas de pagamento", "Gerencia as Formas de pagamento"),
                        new Tag("Pedidos", "Gerencia os Pedidos"),
                        new Tag("Restaurantes", "Gerencia os Restaurantes"),
                        new Tag("Estados", "Gerencia os Estados"),
                        new Tag("Produtos", "Gerencia os Produtos"),
                        new Tag("Usuários", "Gerencia os Usuários"),
                        new Tag("Estatísticas", "Estatísticas da Algafood"),
                        new Tag("Permissões", "Gerencia as Permissões"));
    }

    private List<Response> globalGetResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do Servidor")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemaModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que pode ser aceita pelo consumidor")
                        .build()
        );
    }

    private List<Response> globalPostPutResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida (erro do cliente)")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemaModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do Servidor")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemaModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que pode ser aceita pelo consumidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                        .description("Requisição recusada porque o corpo está em um formato não suportado")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemaModelReference())
                        .build()
        );
    }

    private List<Response> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida (erro do cliente)")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemaModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do servidor")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemaModelReference())
                        .build()
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Algafood API")
                .description("API aberta para clientes e restaurantes.")
                .version("1")
                .contact(new Contact("@gabrielgua", "https://github.com/gabrielgua", "gabriel.guaita45@gmail.com"))
                .build();
    }

    @Bean
    public JacksonModuleRegistrar springFoxJacksonConfig() {
        return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
    }

    private Consumer<RepresentationBuilder> getProblemaModelReference() {
        return r -> r.model(m -> m.name("Problema")
                .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
                        q -> q.name("Problema").namespace("com.gabriel.algafood.api.exceptionhandler")
                ))));
    }


}
