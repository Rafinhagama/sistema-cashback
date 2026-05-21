package com.cashback.dto;

import java.math.BigDecimal;

public class GerarCashbackRequest {

    private String clienteId;
    private String pedidoId;
    private BigDecimal valorPedido;
    private BigDecimal valorPagoEmDinheiro;
    private BigDecimal percentualCashback;
    private Boolean aprovacaoInstantanea;
 

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

    public BigDecimal getValorPagoEmDinheiro() {
        return valorPagoEmDinheiro;
    }

    public void setValorPagoEmDinheiro(BigDecimal valorPagoEmDinheiro) {
        this.valorPagoEmDinheiro = valorPagoEmDinheiro;
    }

    public BigDecimal getPercentualCashback() {
        return percentualCashback;
    }

    public void setPercentualCashback(BigDecimal percentualCashback) {
        this.percentualCashback = percentualCashback;
    }
    
    
    public Boolean getAprovacaoInstantanea() {
        return aprovacaoInstantanea;
    }

    public void setAprovacaoInstantanea(Boolean aprovacaoInstantanea) {
        this.aprovacaoInstantanea = aprovacaoInstantanea;
    }
}