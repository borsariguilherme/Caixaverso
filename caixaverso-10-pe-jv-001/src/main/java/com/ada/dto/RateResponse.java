package com.ada.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.UUID;

public record RateResponse(

        UUID clientId,
        Integer taxaJurosMensal

) {}
