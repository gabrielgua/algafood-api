package com.gabriel.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "/Entidade em uso"),
    NEGOCIO_ERRO("/negocio-erro", "Violação de regra de negócio"),
    CORPO_NAO_LEGIVEL("/corpo-nao-legivel", "Corpo não legível"),
    TIPO_DE_CONTEUDO_NAO_SUPORTADO("/tipo-de-conteudo-nao-suportado", "Tipo de conteúdo não suportado"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro Inválido"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://algafood.com.br" +path;
        this.title = title;

    }
}
