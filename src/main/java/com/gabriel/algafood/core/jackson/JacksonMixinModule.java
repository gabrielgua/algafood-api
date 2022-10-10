package com.gabriel.algafood.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gabriel.algafood.api.model.mixin.CozinhaMixin;
import com.gabriel.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
    }
}
