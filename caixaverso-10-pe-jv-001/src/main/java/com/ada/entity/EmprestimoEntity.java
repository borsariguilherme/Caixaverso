package com.ada.entity;
import com.ada.dto.Parcela;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigInteger;
import java.util.List;

@Entity
public class EmprestimoEntity {

    String id,
    String clienteId,
    BigInteger valorTotal,
    int quantidadeParcelas,
    String tipoAmortizacao,
    Double taxaJurosMensal,
    String status,
    List<Parcela> parcelas;

}
