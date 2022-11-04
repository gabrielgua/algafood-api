package com.gabriel.algafood.core.email;

import com.gabriel.algafood.domain.service.EnvioEmailService;
import com.gabriel.algafood.infrastructure.service.email.FakeEnvioEmailService;
import com.gabriel.algafood.infrastructure.service.email.SandBoxEnvioEmailService;
import com.gabriel.algafood.infrastructure.service.email.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {
        switch (emailProperties.getImpl()) {
            case FAKE: return new FakeEnvioEmailService();
            case SMTP: return new SmtpEnvioEmailService();
            case SANDBOX: return new SandBoxEnvioEmailService();
            default: return null;
        }
    }
}
