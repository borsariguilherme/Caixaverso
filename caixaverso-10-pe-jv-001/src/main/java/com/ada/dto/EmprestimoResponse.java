package com.ada.dto;

import com.ada.model.enums.StatusEmprestimo;
import com.ada.model.enums.TipoAmortizacao;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record EmprestimoResponse(

        @Schema(example = "a38c2020-f463-4b6a-986c-7e3f81ad50c4")
        UUID id,

        @Schema(example = "123e4567-e89b-12d3-a456-426614174000")
        UUID clienteId,

        @Schema(example = "10000")
        BigDecimal valorTotal,

        @Schema(example = "12")
        int quantidadeParcelas,

        @Schema(example = "SAC")
        TipoAmortizacao tipoAmortizacao,

        @Schema(example = "2")
        Integer taxaJurosMensal,

        @Schema(example = "PENDENTE")
        StatusEmprestimo status,

        List<ParcelaResponse> parcelas

) {}
