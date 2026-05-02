package com.ada.service;

import com.ada.client.TaxaClient;
import com.ada.dto.EmprestimoRequest;
import com.ada.dto.EmprestimoResponse;
import com.ada.dto.ParcelaResponse;
import com.ada.dto.RateResponse;
import com.ada.model.entity.EmprestimoEntity;
import com.ada.model.entity.ParcelaEntity;
import com.ada.model.enums.StatusEmprestimo;
import com.ada.repository.EmprestimoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EmprestimoService {

    @Inject
    EmprestimoRepository repository;

    @Inject
    @RestClient
    TaxaClient taxaClient;

    @Inject
    EmprestimoCalculator calculator;

    // READ
    public List<EmprestimoResponse> consultaById(UUID clientId) {
        return repository.find("clienteId", clientId)
                .stream()
                .map(this::emprestimoToResponse)
                .toList();
    }

    // CREATE
    public EmprestimoResponse incluiEmprestimo(EmprestimoRequest request) {

        // 1. consulta taxa externa
        RateResponse rate = taxaClient.consultarTaxa(
                request.clienteId(),
                request.tipoAmortizacao().name()
        );

        // 2. calcula parcelas
        List<ParcelaEntity> parcelas = calculator.calcular(
                request,
                rate.taxaJurosMensal()
        );

        // 3. monta entity
        EmprestimoEntity entity = new EmprestimoEntity();
        entity.clienteId = request.clienteId();
        entity.valorTotal = request.valorTotal();
        entity.quantidadeParcelas = request.quantidadeParcelas();
        entity.tipoAmortizacao = request.tipoAmortizacao();
        entity.taxaJurosMensal = rate.taxaJurosMensal();
        entity.status = StatusEmprestimo.PENDENTE;

        // vínculo bidirecional
        parcelas.forEach(p -> p.emprestimo = entity);
        entity.parcelas = parcelas;

        // 4. persiste tudo (cascade)
        repository.persist(entity);

        // 5. retorna response
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

