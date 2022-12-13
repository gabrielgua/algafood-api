package com.gabriel.algafood.domain.listener;

import com.gabriel.algafood.domain.event.PedidoConfirmadoEvent;
import com.gabriel.algafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

//    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    @TransactionalEventListener // AFTER_COMMIT <- padrão
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        var pedido = event.getPedido();
        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido Confirmado")
                .corpo("emails/pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmailService.enviar(mensagem);
    }
}
