package com.soulware.medicalhistory.infrastructure.adapters.out.persistence;

import com.soulware.medicalhistory.application.ports.out.ClinicalFolderStatusRepository;
import com.soulware.medicalhistory.domain.model.entities.ClinicalFolderStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

@ApplicationScoped
public class JpaClinicalFolderStatusRepository implements ClinicalFolderStatusRepository {

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    public JpaClinicalFolderStatusRepository() {
    }

    @Override
    public Optional<ClinicalFolderStatus> findByName(String name) {
        try {
            ClinicalFolderStatus status = entityManager
                    .createQuery("SELECT s FROM ClinicalFolderStatus s WHERE s.name = :name", ClinicalFolderStatus.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.of(status);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
