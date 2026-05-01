package com.ada.dto;

import com.ada.model.TipoAmortizacao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

public record EmprestimoRequest(

        UUID clienteId,
        BigDecimal valorTotal,
        int quantidadeParcelas,
        TipoAmortizacao tipoAmortizacao

) {}
