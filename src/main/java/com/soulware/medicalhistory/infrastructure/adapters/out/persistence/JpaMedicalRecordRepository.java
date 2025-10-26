package com.soulware.medicalhistory.infrastructure.adapters.out.persistence;

import com.soulware.medicalhistory.application.ports.out.MedicalRecordRepository;
import com.soulware.medicalhistory.domain.model.aggregates.MedicalRecord;
import com.soulware.medicalhistory.domain.queries.GetMedicalRecordByPatientAndVersionNumberQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.List;

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

    @Override
    public MedicalRecord findMedicalRecordByPatientAndVersionNumber(GetMedicalRecordByPatientAndVersionNumberQuery query) {
        String jpql = """
    SELECT mr FROM MedicalRecord mr
    JOIN FETCH mr.assessmentRecord
    JOIN mr.clinicalFolder mh
    WHERE mh.patientId = :patientId
    AND mr.versionNumber = :versionNumber
    """;

        try {
            return em.createQuery(jpql, MedicalRecord.class)
                    .setParameter("patientId", query.patient())
                    .setParameter("versionNumber", query.versionNumber())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
