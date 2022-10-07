package com.gabriel.algafood.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gabriel.algafood.api.model.mixin.CozinhaMixin;
import com.gabriel.algafood.api.model.mixin.RestauranteMixin;
import com.gabriel.algafood.domain.model.Cozinha;
import com.gabriel.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
    }
}
