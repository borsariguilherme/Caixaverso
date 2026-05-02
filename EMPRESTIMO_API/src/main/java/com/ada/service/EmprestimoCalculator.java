package com.ada.service;

import com.ada.dto.request.EmprestimoRequest;
import com.ada.model.entity.ParcelaEntity;
import com.ada.model.enums.StatusParcela;
import com.ada.model.enums.TipoAmortizacao;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    // ================= SAC =================
    private List<ParcelaEntity> calcularSAC(EmprestimoRequest request, Integer taxaJuros) {

        List<ParcelaEntity> parcelas = new ArrayList<>();

        BigDecimal principal = request.valorTotal();
        int n = request.quantidadeParcelas();
        BigDecimal taxa = percentual(taxaJuros);

        BigDecimal amortizacao = principal.divide(BigDecimal.valueOf(n), 2, RoundingMode.HALF_UP);
        BigDecimal saldoDevedor = principal;

        for (int i = 1; i <= n; i++) {

            BigDecimal juros = saldoDevedor.multiply(taxa).setScale(2, RoundingMode.HALF_UP);
            BigDecimal prestacao = amortizacao.add(juros);

            saldoDevedor = saldoDevedor.subtract(amortizacao).max(BigDecimal.ZERO);

            parcelas.add(criarParcela(i, amortizacao, juros, prestacao, saldoDevedor));
        }

        return parcelas;
    }

    // ================= PRICE =================
    private List<ParcelaEntity> calcularPRICE(EmprestimoRequest request, Integer taxaJuros) {

        List<ParcelaEntity> parcelas = new ArrayList<>();

        BigDecimal principal = request.valorTotal();
        int n = request.quantidadeParcelas();
        BigDecimal taxa = percentual(taxaJuros);

        // PMT = P * [ i(1+i)^n ] / [ (1+i)^n - 1 ]
        BigDecimal fator = taxa.add(BigDecimal.ONE).pow(n);
        BigDecimal pmt = principal.multiply(taxa.multiply(fator))
                .divide(fator.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);

        BigDecimal saldoDevedor = principal;

        for (int i = 1; i <= n; i++) {

            BigDecimal juros = saldoDevedor.multiply(taxa).setScale(2, RoundingMode.HALF_UP);
            BigDecimal amortizacao = pmt.subtract(juros).setScale(2, RoundingMode.HALF_UP);

            saldoDevedor = saldoDevedor.subtract(amortizacao).max(BigDecimal.ZERO);

            parcelas.add(criarParcela(i, amortizacao, juros, pmt, saldoDevedor));
        }

        return parcelas;
    }

    // ================= Utils =================

    private BigDecimal percentual(Integer taxaJuros) {
        return BigDecimal.valueOf(taxaJuros)
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
    }

    private ParcelaEntity criarParcela(
            int ordem,
            BigDecimal amortizacao,
            BigDecimal juros,
            BigDecimal prestacao,
            BigDecimal saldoDevedor
    ) {
        ParcelaEntity p = new ParcelaEntity();

        p.ordem = ordem;
        p.dataVencimento = LocalDate.now().plusMonths(ordem);
        p.valorAmortizacao = amortizacao;
        p.valorJuros = juros;
        p.valorPrestacao = prestacao;
        p.saldoDevedor = saldoDevedor;
        p.status = StatusParcela.PENDENTE;

        return p;
    }
}
