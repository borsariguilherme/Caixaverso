package com.ada.repository;

import com.ada.model.EmprestimoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmprestimoRepository implements PanacheRepository<EmprestimoEntity> {
}
