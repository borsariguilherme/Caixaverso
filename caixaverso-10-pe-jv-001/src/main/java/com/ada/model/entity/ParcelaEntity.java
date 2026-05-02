package com.ada.model.entity;

import com.ada.model.enums.StatusParcela;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

@Entity
@Table(name = "parcela")
public class ParcelaEntity {

    @Id
    @GeneratedValue
    @Schema(example = "ba7d4c2b-8a1a-4d2a-a92c-68e1ab4c885c")
    public UUID id;

    @NotNull
    @Min(1)
    @Schema(description = "Ordem da parcela", example = "1")
    public Integer ordem;

    @NotNull
    @Schema(description = "Data de vencimento", example = "2024-05-15")
    public LocalDate dataVencimento;

    @NotNull
    @DecimalMin("0.0")
    @Schema(description = "Valor da amortização", example = "833.33")
    public BigDecimal valorAmortizacao;

    @NotNull
    @DecimalMin("0.0")
    @Schema(description = "Valor dos juros", example = "200")
    public BigDecimal valorJuros;

    @NotNull
    @DecimalMin("0.0")
    @Schema(description = "Valor total da prestação", example = "1033.33")
    public BigDecimal valorPrestacao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Schema(
            description = "Status da parcela",
            example = "PENDENTE",
            enumeration = {"PENDENTE", "PAGA", "CANCELADA"}
    )
    public StatusParcela status;

    @NotNull
    @DecimalMin("0.0")
    @Schema(description = "Saldo devedor após a parcela", example = "9166.67")
    public BigDecimal saldoDevedor;

    // 🔗 Relacionamento com empréstimo
    @ManyToOne
    @JoinColumn(name = "emprestimo_id")
    public EmprestimoEntity emprestimo;
}
