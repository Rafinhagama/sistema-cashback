package com.cashback.dto;

import java.math.BigDecimal;

public class UsarCashbackResponse {

    private BigDecimal valorOriginal;
    private BigDecimal cashbackUtilizado;
    private BigDecimal valorFinal;

    public BigDecimal getValorOriginal() {
        return valorOriginal;
    }

    public void setValorOriginal(BigDecimal valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    public BigDecimal getCashbackUtilizado() {
        return cashbackUtilizado;
    }

    public void setCashbackUtilizado(BigDecimal cashbackUtilizado) {
        this.cashbackUtilizado = cashbackUtilizado;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }
}