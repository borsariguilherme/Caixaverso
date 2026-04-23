package com.ada.dto;

import java.math.BigInteger;
import java.util.List;

public record EmprestimoRequest(
        String id,
        String clienteId,
        BigInteger valorTotal,
        int quantidadeParcelas,
        String tipoAmortizacao,
        Double taxaJurosMensal,
        String status,
        List<Parcela> parcelas
) {}
