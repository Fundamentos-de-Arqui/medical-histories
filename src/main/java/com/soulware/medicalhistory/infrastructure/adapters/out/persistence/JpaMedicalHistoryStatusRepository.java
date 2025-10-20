package com.soulware.medicalhistory.infrastructure.adapters.out.persistence;

import com.soulware.medicalhistory.application.ports.out.MedicalHistoryStatusRepository;
import com.soulware.medicalhistory.domain.model.entities.MedicalHistoryStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

@ApplicationScoped
public class JpaMedicalHistoryStatusRepository implements MedicalHistoryStatusRepository {

    @PersistenceContext(unitName = "default") // 👈 usa el nombre de tu persistence-unit
    private EntityManager entityManager;

    public JpaMedicalHistoryStatusRepository() {
    }

    @Override
    public Optional<MedicalHistoryStatus> findByName(String name) {
        try {
            MedicalHistoryStatus status = entityManager
                    .createQuery("SELECT s FROM MedicalHistoryStatus s WHERE s.name = :name", MedicalHistoryStatus.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.of(status);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
