package com.ada.service;

import com.ada.client.TaxaClient;
import com.ada.dto.request.EmprestimoRequest;
import com.ada.dto.response.EmprestimoResponse;
import com.ada.dto.response.ParcelaResponse;
import com.ada.dto.response.RateResponse;
import com.ada.exception.BusinessException;
import com.ada.model.entity.EmprestimoEntity;
import com.ada.model.entity.ParcelaEntity;
import com.ada.model.enums.StatusEmprestimo;
import com.ada.repository.EmprestimoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
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
                .list()
                .stream()
                .map(this::emprestimoToResponse)
                .toList();
    }

    // CREATE
    @Transactional
    public EmprestimoResponse incluiEmprestimo(EmprestimoRequest request) {

        // 1. consulta taxa externa
        try {

            Response responseTaxaClient = taxaClient.consultarTaxa(
                    request.clienteId(),
                    request.tipoAmortizacao().name());

            RateResponse rate = responseTaxaClient.readEntity(RateResponse.class);

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

        } catch (RuntimeException e) {

            if (e.getMessage().equals("CREDITO_REPROVADO")) {
                throw new BusinessException("CLI-0", "Cliente não elegível");
            }

            throw e;
        }
    }

    // DELETE
    @Transactional
    public void deletaEmprestimo(UUID id) {

        EmprestimoEntity entity = repository.findById(id);

        if (entity == null) {
            throw new NotFoundException("Insucesso na localização prévia do registro para remoção.");
        }

        repository.delete(entity);

    }

    // MAPPERS
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

