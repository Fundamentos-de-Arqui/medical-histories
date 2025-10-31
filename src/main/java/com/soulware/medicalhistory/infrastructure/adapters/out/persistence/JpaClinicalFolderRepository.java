package com.soulware.medicalhistory.infrastructure.adapters.out.persistence;

import com.soulware.medicalhistory.application.ports.out.ClinicalFolderRepository;
import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;
import com.soulware.medicalhistory.domain.model.aggregates.MedicalRecord;
import com.soulware.medicalhistory.domain.model.valueobjects.ClinicalFolderId;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;
import com.soulware.medicalhistory.domain.queries.GetClinicalFolderByPatientIdQuery;
import com.soulware.medicalhistory.domain.queries.GetClinicalFoldersQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
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
    public ClinicalFolder getClinicalFolderByPatientId(GetClinicalFolderByPatientIdQuery query) {
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

    private MedicalRecord loadFirstMedicalRecord(ClinicalFolder folder) {
        String jpql = """
        SELECT mr FROM MedicalRecord mr
        JOIN FETCH mr.assessmentRecord
        WHERE mr.clinicalFolder = :folder
        ORDER BY mr.versionNumber ASC
    """;

        return em.createQuery(jpql, MedicalRecord.class)
                .setParameter("folder", folder)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<ClinicalFolder> getAllClinicalFolders(GetClinicalFoldersQuery query) {
        String jpql = """
        SELECT cf FROM ClinicalFolder cf
        LEFT JOIN FETCH cf.status
        WHERE cf.status.name = :status
        ORDER BY cf.id
    """;

        List<ClinicalFolder> folders = em.createQuery(jpql, ClinicalFolder.class)
                .setParameter("status", query.status())
                .setFirstResult(query.page() * query.size())
                .setMaxResults(query.size())
                .getResultList();

        folders.forEach(folder -> {
            MedicalRecord firstRecord = loadFirstMedicalRecord(folder);
            folder.getMedicalRecords().clear();
            if (firstRecord != null) {
                folder.getMedicalRecords().add(firstRecord);
            }
        });

        return folders;
    }

    @Override
    public long countByStatus(String status) {
        return em.createQuery("""
        SELECT COUNT(cf) FROM ClinicalFolder cf
        WHERE cf.status.name = :status
    """, Long.class)
                .setParameter("status", status)
                .getSingleResult();
    }


}