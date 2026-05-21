package com.cashback.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cashback.dto.WinthorPedidoResponse;

@Service
public class WinthorService {

    @Value("${winthor.api.base-url}")
    private String winthorBaseUrl;

    @Value("${winthor.api.token}")
    private String winthorToken;

    private final RestTemplate restTemplate = new RestTemplate();

    public WinthorPedidoResponse buscarPedido(String numeroPedido) {

        /*
         * Este endpoint é exemplo.
         * O caminho real precisa ser confirmado na documentação/API do Winthor do cliente.
         */
        String url = winthorBaseUrl + "/pedidos/" + numeroPedido;

        /*
         * Aqui ainda precisa adaptar a autenticação conforme o Winthor do cliente.
         * Pode ser token, Basic Auth, usuário/senha etc.
         */

        // Exemplo temporário para testar sem Winthor real:
        WinthorPedidoResponse response = new WinthorPedidoResponse();
        response.setNumeroPedido(numeroPedido);
        response.setClienteId("CLI-001");
        response.setValorPedido(new BigDecimal("300.00"));
        response.setStatusPedido("FATURADO");

        return response;
    }
}