package com.cashback.dto;

import java.math.BigDecimal;

public class PedidoAutomaticoDTO {

    private String clienteId;
    private String pedidoId;
    private BigDecimal valorPedido;
    private Boolean pagoDentroPrazo;
    private Integer diasUltimoPedido;
    private String statusPedido;

    private Boolean elegivel;
    private BigDecimal valorCashback;
    private String mensagem;

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

    public Boolean getPagoDentroPrazo() {
        return pagoDentroPrazo;
    }

    public void setPagoDentroPrazo(Boolean pagoDentroPrazo) {
        this.pagoDentroPrazo = pagoDentroPrazo;
    }

    public Integer getDiasUltimoPedido() {
        return diasUltimoPedido;
    }

    public void setDiasUltimoPedido(Integer diasUltimoPedido) {
        this.diasUltimoPedido = diasUltimoPedido;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    public Boolean getElegivel() {
        return elegivel;
    }

    public void setElegivel(Boolean elegivel) {
        this.elegivel = elegivel;
    }

    public BigDecimal getValorCashback() {
        return valorCashback;
    }

    public void setValorCashback(BigDecimal valorCashback) {
        this.valorCashback = valorCashback;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}