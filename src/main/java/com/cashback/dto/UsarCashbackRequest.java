package com.cashback.dto;

import java.math.BigDecimal;

public class UsarCashbackRequest {

    private String clienteId;
    private String pedidoId;
    private BigDecimal valorPedido;
    private BigDecimal percentualMaximoUso;

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(String pedidoId) {
        this.pedidoId = pedidoId;
    }

    public BigDecimal getValorPedido() {
        return valorPedido;
    }

    public void setValorPedido(BigDecimal valorPedido) {
        this.valorPedido = valorPedido;
    }

    public BigDecimal getPercentualMaximoUso() {
        return percentualMaximoUso;
    }

    public void setPercentualMaximoUso(BigDecimal percentualMaximoUso) {
        this.percentualMaximoUso = percentualMaximoUso;
    }
}