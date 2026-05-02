package com.ada.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record ErrorResponse(

        String codigo,
        String mensagem

) {}
