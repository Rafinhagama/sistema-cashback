package com.cashback.dto;

import java.math.BigDecimal;

public class CashbackAutomaticoRequest {

    private BigDecimal percentualCashback;
    private Integer diasUltimoPedido;
    private String dataReferencia;
    private String statusMinimo;

    public BigDecimal getPercentualCashback() {
        return percentualCashback;
    }

    public void setPercentualCashback(BigDecimal percentualCashback) {
        this.percentualCashback = percentualCashback;
    }

    public Integer getDiasUltimoPedido() {
        return diasUltimoPedido;
    }

    public void setDiasUltimoPedido(Integer diasUltimoPedido) {
        this.diasUltimoPedido = diasUltimoPedido;
    }

    public String getDataReferencia() {
        return dataReferencia;
    }

    public void setDataReferencia(String dataReferencia) {
        this.dataReferencia = dataReferencia;
    }

    public String getStatusMinimo() {
        return statusMinimo;
    }

    public void setStatusMinimo(String statusMinimo) {
        this.statusMinimo = statusMinimo;
    }
}