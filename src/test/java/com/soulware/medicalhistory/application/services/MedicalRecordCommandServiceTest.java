package com.soulware.medicalhistory.application.services;

import com.soulware.medicalhistory.application.ports.out.AssessmentRecordRepository;
import com.soulware.medicalhistory.application.ports.out.ClinicalFolderRepository;
import com.soulware.medicalhistory.application.ports.out.MedicalRecordRepository;
import com.soulware.medicalhistory.application.results.CreatedMedicalRecordResult;
import com.soulware.medicalhistory.domain.commands.CreateMedicalRecordCommand;
import com.soulware.medicalhistory.domain.model.aggregates.*;
import com.soulware.medicalhistory.domain.model.entities.*;
import com.soulware.medicalhistory.domain.model.entities.ClinicalFolderStatus;
import com.soulware.medicalhistory.domain.model.valueobjects.*;
import com.soulware.medicalhistory.infrastructure.adapters.out.persistence.JpaAssessmentRecordRepository;
import com.soulware.medicalhistory.infrastructure.adapters.out.persistence.JpaClinicalFolderRepository;
import com.soulware.medicalhistory.infrastructure.adapters.out.persistence.JpaMedicalRecordRepository;
import org.junit.jupiter.api.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MedicalRecordCommandServiceIntegrationTest {

    private static EntityManagerFactory emf;
    private EntityManager em;

    private ClinicalFolderRepository clinicalFolderRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private AssessmentRecordRepository assessmentRecordRepository;

    private MedicalRecordCommandService medicalRecordCommandService;

    @BeforeAll
    static void init() {
        emf = Persistence.createEntityManagerFactory("test-pu"); // Usa persistence.xml con H2
    }

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        clinicalFolderRepository = new JpaClinicalFolderRepository(em);
        medicalRecordRepository = new JpaMedicalRecordRepository(em);
        assessmentRecordRepository = new JpaAssessmentRecordRepository(em);
        medicalRecordCommandService = new MedicalRecordCommandService(
                clinicalFolderRepository,
                medicalRecordRepository,
                assessmentRecordRepository
        );
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
    void shouldCreateMedicalRecordAndAssessmentRecordSuccessfully() {
        // Arrange
        em.getTransaction().begin();

        var status = new ClinicalFolderStatus(
                new ClinicalFolderStatusId(1),
                "ACTIVE",
                "Active clinical folder"
        );
        em.persist(status);

        // Create clinical folder
        var patientId = new PatientId(10);
        ClinicalFolder clinicalFolder = new ClinicalFolder(patientId, status);
        clinicalFolderRepository.save(clinicalFolder);

        // Create command
        var command = new CreateMedicalRecordCommand(
                clinicalFolder.getId(),
                "Postural deviation detected",
                "Rehabilitation plan: 2 sessions per week",
                "Patient shows good attitude toward treatment",
                5, // TherapistId
                "ASSESSMENT",
                LocalDateTime.now()
        );

        // Act
        CreatedMedicalRecordResult result = medicalRecordCommandService.create(command);

        em.getTransaction().commit();

        // Assert
        assertNotNull(result);
        assertNotNull(result.medicalRecord());
        assertNotNull(result.assessmentRecord());

        // Verify persistency
        MedicalRecord persistedMedicalRecord = em.find(
                MedicalRecord.class,
                result.medicalRecord().getId().value()
        );
        assertNotNull(persistedMedicalRecord);

        AssessmentRecord persistedAssessment = em.find(
                AssessmentRecord.class,
                result.assessmentRecord().getId().id()
        );
        assertNotNull(persistedAssessment);

        assertEquals("Postural deviation detected", persistedAssessment.getDiagnostic().diagnostic());
        assertEquals("ASSESSMENT", persistedAssessment.getAssessmentType().name());
        assertEquals(5, persistedAssessment.getTherapistId().value());
    }
}
