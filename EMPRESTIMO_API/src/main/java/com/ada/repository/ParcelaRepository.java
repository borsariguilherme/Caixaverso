package com.ada.repository;

import com.ada.model.entity.ParcelaEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class ParcelaRepository implements PanacheRepositoryBase<ParcelaEntity, UUID> {
}
