package com.ada.dto.request;

import com.ada.model.enums.TipoAmortizacao;
import jakarta.validation.constraints.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

public record EmprestimoRequest(

        @NotNull
        @Schema(example = "123e4567-e89b-12d3-a456-426614174000")
        UUID clienteId,

        @NotNull
        @DecimalMin(value = "100.0", inclusive = true)
        @DecimalMax(value = "10000000.0", inclusive = true)
        @Schema(example = "10000", minimum = "100", maximum = "10000000")
        BigDecimal valorTotal,

        @Min(1)
        @Max(480)
        @Schema(example = "12", minimum = "1", maximum = "480")
        int quantidadeParcelas,

        @NotNull
        @Schema(example = "SAC", enumeration = {"SAC", "PRICE"})
        TipoAmortizacao tipoAmortizacao

) {}
