package com.ada.service;

import com.ada.model.entity.ParcelaEntity;
import com.ada.model.enums.StatusParcela;
import com.ada.repository.ParcelaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ParcelaService {

    @Inject
    ParcelaRepository parcelaRepository;

    // 1. Listar por empréstimo
    public List<ParcelaEntity> listarPorEmprestimo(UUID emprestimoId) {

        return parcelaRepository
                .list("emprestimo.id", emprestimoId);
    }

    // 2. Buscar por ID
    public ParcelaEntity buscarPorId(UUID id) {

        ParcelaEntity parcela = parcelaRepository.findById(id);

        if (parcela == null) {
            throw new NotFoundException("Parcela não encontrada");
        }

        return parcela;
    }

    // 3. Baixar parcela
    @Transactional
    public void baixarParcela(UUID id) {

        ParcelaEntity parcela = parcelaRepository.findById(id);

        if (parcela == null) {
            throw new NotFoundException("Parcela não encontrada");
        }

        if (parcela.status == StatusParcela.PAGA) {
            throw new BadRequestException("Parcela já está paga");
        }

        if (parcela.status == StatusParcela.CANCELADA) {
            throw new BadRequestException("Parcela está cancelada");
        }

        parcela.status = StatusParcela.PAGA;
    }
}
