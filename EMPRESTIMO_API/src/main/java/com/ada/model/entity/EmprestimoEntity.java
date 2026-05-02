package com.ada.model.entity;
import com.ada.model.enums.StatusEmprestimo;
import com.ada.model.enums.TipoAmortizacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.DecimalMin;
import jakarta.persistence.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "emprestimo")
public class EmprestimoEntity {

    @Id
    @GeneratedValue
    @Schema(description = "ID do empréstimo", example = "a38c2020-f463-4b6a-986c-7e3f81ad50c4")
    public UUID id;

    @NotNull
    @Schema(description = "ID do cliente", example = "123e4567-e89b-12d3-a456-426614174000")
    public UUID clienteId;

    @NotNull
    @DecimalMin("0.0")
    @Schema(description = "Valor total do empréstimo", example = "10000")
    public BigDecimal valorTotal;

    @NotNull
    @Min(1)
    @Schema(description = "Quantidade de parcelas", example = "12")
    public int quantidadeParcelas;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Schema(description = "Tipo de amortização", example = "SAC", enumeration = {"SAC", "PRICE"})
    public TipoAmortizacao tipoAmortizacao;

    @NotNull
    @Min(0)
    @Schema(description = "Taxa de juros mensal (multiplicador inteiro)", example = "2")
    public Integer taxaJurosMensal;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Schema(description = "Status do empréstimo", example = "PENDENTE", enumeration = {"PENDENTE", "PAGO"})
    public StatusEmprestimo status;

    @OneToMany(mappedBy = "emprestimo", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ParcelaEntity> parcelas;

}
