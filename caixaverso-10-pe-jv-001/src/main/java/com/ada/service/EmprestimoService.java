package com.ada.service;

import com.ada.dto.EmprestimoRequest;
import com.ada.dto.EmprestimoResponse;
import com.ada.dto.ParcelaResponse;
import com.ada.model.EmprestimoEntity;
import com.ada.model.ParcelaEntity;
import com.ada.repository.EmprestimoRepository;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

public class EmprestimoService {

    @Inject
    EmprestimoRepository repository;

    // READ
    public List<EmprestimoResponse> consultaById(UUID clientId) {
        return repository.find("clienteId", clientId)
                .stream()
                .map(this::emprestimoToResponse)
                .toList();
    }

    // CREATE
    public EmprestimoResponse incluiEmprestimo(EmprestimoRequest request) {
        EmprestimoEntity entity = new EmprestimoEntity();
        // map request → entity (idealmente via mapper) //TODO
        repository.persist(entity);

        return emprestimoToResponse(entity);
    }

    // DELETE
    public void deletaEmprestimo(UUID clientId) {
        repository.delete("clienteId", clientId);
    }

    // MAPPERS (privado)
    private EmprestimoResponse emprestimoToResponse(EmprestimoEntity e) {
        return new EmprestimoResponse(
                e.id,
                e.clienteId,
                e.valorTotal,
                e.quantidadeParcelas,
                e.tipoAmortizacao,
                e.taxaJurosMensal,
                e.status,
                e.parcelas == null ? List.of() :
                        e.parcelas.stream()
                                .map(this::parcelaToResponse)
                                .toList()
        );
    }

    private ParcelaResponse parcelaToResponse(ParcelaEntity p) {
        return new ParcelaResponse(
                p.id,
                p.ordem,
                p.dataVencimento,
                p.valorAmortizacao,
                p.valorJuros,
                p.valorPrestacao,
                p.status,
                p.saldoDevedor
        );
    }
}

