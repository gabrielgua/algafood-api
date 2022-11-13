package com.gabriel.algafood.infrastructure.service.email;

import com.gabriel.algafood.core.email.EmailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class SandBoxEnvioEmailService extends SmtpEnvioEmailService{

    @Autowired
    private EmailProperties emailProperties;

    @Override
    protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
        var mimeMessage = super.criarMimeMessage(mensagem);
        var helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo(emailProperties.getSandbox().getDestinatario());
        return mimeMessage;
    }
}