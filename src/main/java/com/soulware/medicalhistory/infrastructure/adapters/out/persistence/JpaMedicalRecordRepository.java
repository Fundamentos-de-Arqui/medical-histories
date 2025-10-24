package com.soulware.medicalhistory.infrastructure.adapters.out.persistence;

import com.soulware.medicalhistory.application.ports.out.MedicalRecordRepository;
import com.soulware.medicalhistory.domain.model.aggregates.MedicalRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class JpaMedicalRecordRepository implements MedicalRecordRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(MedicalRecord medicalRecord) {
        if (medicalRecord.getId().value() == 0) {
            em.persist(medicalRecord);
            em.flush();
        }
        else {
            em.merge(medicalRecord);
        }
    }


}
