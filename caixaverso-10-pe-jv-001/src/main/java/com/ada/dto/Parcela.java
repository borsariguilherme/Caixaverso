package com.ada.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Parcela (

    String id,
    int ordem,
    LocalDate dataVencimento,
    BigDecimal valorAmortizacao,
    BigDecimal valorJuros,
    BigDecimal valorPrestacao,
    String status,
    BigDecimal saldoDevedor

){}
