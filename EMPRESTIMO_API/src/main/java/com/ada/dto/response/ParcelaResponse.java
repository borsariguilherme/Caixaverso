package com.ada.dto.response;

import com.ada.model.enums.StatusParcela;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ParcelaResponse(
        UUID id,
        Integer ordem,
        LocalDate dataVencimento,
        BigDecimal valorAmortizacao,
        BigDecimal valorJuros,
        BigDecimal valorPrestacao,
        StatusParcela status,
        BigDecimal saldoDevedor
) {}
