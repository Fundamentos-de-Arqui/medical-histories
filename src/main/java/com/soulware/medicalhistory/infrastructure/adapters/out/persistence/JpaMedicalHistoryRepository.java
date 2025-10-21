package com.soulware.medicalhistory.infrastructure.adapters.out.persistence;

import com.soulware.medicalhistory.application.ports.out.MedicalHistoryRepository;
import com.soulware.medicalhistory.domain.model.aggregates.MedicalHistory;
import com.soulware.medicalhistory.domain.model.valueobjects.MedicalHistoryId;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class JpaMedicalHistoryRepository implements MedicalHistoryRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(MedicalHistory medicalHistory) {
        if (medicalHistory.getId() == 0) {
            em.persist(medicalHistory);
        } else {
            em.merge(medicalHistory);
        }
    }

    @Override
    @Transactional
    public Optional<MedicalHistory> findById(MedicalHistoryId id) {
        return em.createQuery(
                        "SELECT m FROM MedicalHistory m JOIN FETCH m.status WHERE m.id = :id",
                        MedicalHistory.class
                )
                .setParameter("id", id.getValue())
                .getResultStream()
                .findFirst();
    }

    @Override
    public Optional<MedicalHistory> findByPatientId(PatientId patientId) {
        return em.createQuery("SELECT m FROM MedicalHistory m WHERE m.patientId = :pid", MedicalHistory.class)
                .setParameter("pid", patientId.value())
                .getResultStream()
                .findFirst();
    }
}