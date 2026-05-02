package com.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record ErrorResponse(

        @Schema(
                description = "Identificador padronizado da causa do erro.",
                example = "CREDITO_REPROVADO"
        )
        String codigo,

        @Schema(
                description = "Detalhamento explícito destinado ao mapeamento da UI do sistema.",
                example = "O cliente informado não atendeu aos critérios técnicos de elegibilidade."
        )
        String mensagem

) {}
