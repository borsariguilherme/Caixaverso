package com.ada.dto.response;

import java.util.UUID;

public record RateResponse(

        UUID clientId,
        Integer taxaJurosMensal

) {}
