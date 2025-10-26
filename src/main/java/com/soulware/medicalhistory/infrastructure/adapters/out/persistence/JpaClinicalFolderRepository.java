package com.soulware.medicalhistory.infrastructure.adapters.out.persistence;

import com.soulware.medicalhistory.application.ports.out.ClinicalFolderRepository;
import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;
import com.soulware.medicalhistory.domain.model.valueobjects.ClinicalFolderId;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;
import com.soulware.medicalhistory.domain.queries.GetMedicalFolderByPatientIdQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class JpaClinicalFolderRepository implements ClinicalFolderRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(ClinicalFolder clinicalFolder) {
        if (clinicalFolder.getId() == 0) {
            em.persist(clinicalFolder);
        } else {
            em.merge(clinicalFolder);
        }
    }

    @Override
    @Transactional
    public Optional<ClinicalFolder> findById(ClinicalFolderId id) {
        return em.createQuery(
                        "SELECT m FROM ClinicalFolder m JOIN FETCH m.status WHERE m.id = :id",
                        ClinicalFolder.class
                )
                .setParameter("id", id.getValue())
                .getResultStream()
                .findFirst();
    }

    @Override
    public Optional<ClinicalFolder> findByPatientId(PatientId patientId) {
        return em.createQuery(
                        "SELECT m FROM ClinicalFolder m JOIN FETCH m.status WHERE m.patientId = :pid",
                        ClinicalFolder.class
                )
                .setParameter("pid", patientId.value())
                .getResultStream()
                .findFirst();
    }

    @Override
    public ClinicalFolder getClinicalFolderByPatientId(GetMedicalFolderByPatientIdQuery query) {
        // Paso 1: obtener la ClinicalFolder con sus MedicalRecords
        String jpql1 = """
        SELECT DISTINCT cf FROM ClinicalFolder cf
        LEFT JOIN FETCH cf.status
        LEFT JOIN FETCH cf.medicalRecords
        WHERE cf.patientId = :patientId
    """;

        ClinicalFolder folder;
        try {
            folder = em.createQuery(jpql1, ClinicalFolder.class)
                    .setParameter("patientId", query.patientId())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        // Paso 2: inicializar los AssessmentRecord asociados (Hibernate-level)
        folder.getMedicalRecords().forEach(mr -> {
            if (mr.getAssessmentRecord() != null) {
                // fuerza la inicialización del proxy si aún no está cargado
                mr.getAssessmentRecord().getDiagnostic();
            }
        });

        return folder;
    }


}