package com.ada.dto;

import java.math.BigInteger;
import java.util.List;

public record EmprestimoRequest(

        String clienteId,
        BigInteger valorTotal,
        int quantidadeParcelas,
        String tipoAmortizacao //TODO enum

) {}
