package com.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.UUID;

public record RateResponse(

        @Schema(
                description = "O identificador único de base UUID que foi avaliado.",
                example = "123e4567-e89b-12d3-a456-426614174000"
        )
        UUID clientId,

        @Schema(
                description = "Taxa de juros do mês indexada em formato inteiro (ex. 2 corresponde a 2% ao mês).",
                example = "2"
        )
        Integer taxaJurosMensal

) {}
