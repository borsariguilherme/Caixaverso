package com.ada.service;

import com.ada.dto.EmprestimoRequest;
import com.ada.model.entity.ParcelaEntity;
import com.ada.model.enums.TipoAmortizacao;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class EmprestimoCalculator {

    public List<ParcelaEntity> calcular(
            EmprestimoRequest request,
            Integer taxaJuros
    ) {

        if (request.tipoAmortizacao() == TipoAmortizacao.SAC) {
            return calcularSAC(request, taxaJuros);
        } else {
            return calcularPRICE(request, taxaJuros);
        }
    }

    private List<ParcelaEntity> calcularSAC(...) {
        // regra SAC aqui
        return List.of();
    }

    private List<ParcelaEntity> calcularPRICE(...) {
        // regra PRICE aqui
        return List.of();
    }
}
