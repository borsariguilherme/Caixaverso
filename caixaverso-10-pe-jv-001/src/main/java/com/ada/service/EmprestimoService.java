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
    EmprestimoRepository emprestimoRepository;

    public List<EmprestimoResponse> consultaById(String clientId){

        UUID clientUUID = UUID.fromString(clientId);

        return emprestimoRepository
                .find("clienteId", clientUUID)
                .stream()
                .map(this::emprestimoToResponse)
                .toList();
    }


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

public EmprestimoResponse incluiEmprestimo(EmprestimoRequest emprestimoRequest){

    return emprestimoRepository.add(emprestimoRequest);
}

public void deletaEmprestimo(String clientId){

    UUID clientUUID = UUID.fromString(clientId);

    return emprestimoRepository.delete(clientUUID);
}


}

