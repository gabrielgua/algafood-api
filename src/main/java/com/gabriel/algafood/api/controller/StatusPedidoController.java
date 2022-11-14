package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.openapi.controller.StatusPedidoControllerOpenApi;
import com.gabriel.algafood.domain.service.StatusPedidoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/pedidos/{codigoPedido}")
public class StatusPedidoController  implements StatusPedidoControllerOpenApi {

    private StatusPedidoService statusService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmarPedido(@PathVariable String codigoPedido) {
        statusService.confirmar(codigoPedido);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelarPedido(@PathVariable String codigoPedido) {
        statusService.cancelar(codigoPedido);
    }

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmarEntrega(@PathVariable String codigoPedido) {
        statusService.confirmarEntrega(codigoPedido);
    }
}
