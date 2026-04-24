package com.ada.service;

import com.ada.dto.EmprestimoRequest;
import com.ada.dto.EmprestimoResponse;
import com.ada.repository.EmprestimoRepository;
import jakarta.inject.Inject;

import java.util.List;

public class EmprestimoService {

    @Inject
    EmprestimoRepository emprestimoRepository;

    public List<EmprestimoResponse> consultaById(String clientId){

        return emprestimoRepository.streamAll()
                .filter(e -> e.clienteId.equals(clientId)) // filtra
                .map(e -> new EmprestimoResponse(
                        e.id,
                        e.clienteId,
                        e.valorTotal,
                        e.quantidadeParcelas,
                        e.tipoAmortizacao,
                        e.taxaJurosMensal,
                        e.status,
                        e.parcelas
                ))
                .toList();
    }
    }

    public EmprestimoResponse incluiEmprestimo(EmprestimoRequest emprestimoRequest){

        return emprestimoRepository.add(emprestimoRequest);
    }

    public void deletaEmprestimo(String clientId){

        return emprestimoRepository.delete(emprestimoRequest);
    }


}
