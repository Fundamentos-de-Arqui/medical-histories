package com.soulware.medicalhistory.infrastructure.adapters.out.persistence;

import com.soulware.medicalhistory.application.ports.out.AssessmentRecordRepository;
import com.soulware.medicalhistory.domain.model.entities.AssessmentRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class JpaAssessmentRecordRepository implements AssessmentRecordRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(AssessmentRecord record) {
        if (record.getId().id() == 0) {
            em.persist(record);
            em.flush();
        }
        else {
            em.merge(record);
        }
    }
}
