package com.soulware.medicalhistory.application.services;

import com.soulware.medicalhistory.domain.model.aggregates.*;
import com.soulware.medicalhistory.domain.model.entities.*;
import com.soulware.medicalhistory.domain.model.entities.ClinicalFolderStatus;
import com.soulware.medicalhistory.domain.model.valueobjects.*;
import com.soulware.medicalhistory.domain.queries.GetMedicalRecordByPatientAndVersionNumberQuery;
import com.soulware.medicalhistory.infrastructure.adapters.out.persistence.*;

import jakarta.persistence.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class MedicalRecordQueryServiceIntegrationTest {

    private static EntityManagerFactory emf;
    private EntityManager em;

    private JpaMedicalRecordRepository medicalRecordRepository;
    private JpaClinicalFolderRepository clinicalFolderRepository;
    private JpaAssessmentRecordRepository assessmentRecordRepository;

    private MedicalRecordQueryService medicalRecordQueryService;

    @BeforeAll
    static void init() {
        emf = Persistence.createEntityManagerFactory("test-pu");
    }

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        medicalRecordRepository = new JpaMedicalRecordRepository(em);
        clinicalFolderRepository = new JpaClinicalFolderRepository(em);
        assessmentRecordRepository = new JpaAssessmentRecordRepository(em);

        medicalRecordQueryService = new MedicalRecordQueryService(medicalRecordRepository);
    }

    @AfterEach
    void tearDown() {
        if (em.isOpen()) em.close();
    }

    @AfterAll
    static void close() {
        emf.close();
    }

    @Test
    void shouldReturnMedicalRecordByVersionOrLatestSuccessfully() {
        em.getTransaction().begin();

        var status = new ClinicalFolderStatus(
                new ClinicalFolderStatusId(1),
                "ACTIVE",
                "Active clinical folder"
        );
        em.persist(status);

        var patientId = new PatientId(1);
        var clinicalFolder = new ClinicalFolder(patientId, status);
        clinicalFolderRepository.save(clinicalFolder);

        var recordV1 = new MedicalRecord(clinicalFolder);
        recordV1.setVersionNumber(new VersionNumber(1));
        medicalRecordRepository.save(recordV1);

        var recordV2 = new MedicalRecord(clinicalFolder);
        recordV2.setVersionNumber(new VersionNumber(2));
        medicalRecordRepository.save(recordV2);

        em.getTransaction().commit();

        // Act 1: Buscar por versión específica
        var queryByVersion = new GetMedicalRecordByPatientAndVersionNumberQuery(1, 1);
        var resultV1 = medicalRecordQueryService.getMedicalRecordByPatientAndVersionNumber(queryByVersion);

        // Act 2: Buscar última versión (null)
        var queryLatest = new GetMedicalRecordByPatientAndVersionNumberQuery(1, null);
        var resultLatest = medicalRecordQueryService.getMedicalRecordByPatientAndVersionNumber(queryLatest);

        // Assert
        assertNotNull(resultV1, "Debe devolver el registro de versión 1");
        assertEquals(1, resultV1.getVersionNumber().version(), "La versión debe ser 1");

        assertNotNull(resultLatest, "Debe devolver el último registro");
        assertEquals(2, resultLatest.getVersionNumber().version(), "La versión más reciente debe ser 2");
    }
}
