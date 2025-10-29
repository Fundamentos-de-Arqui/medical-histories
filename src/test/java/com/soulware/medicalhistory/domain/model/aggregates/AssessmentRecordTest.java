package com.soulware.medicalhistory.domain.model.aggregates;

import com.soulware.medicalhistory.domain.model.entities.AssessmentRecord;
import com.soulware.medicalhistory.domain.model.entities.ClinicalFolderStatus;
import com.soulware.medicalhistory.domain.model.valueobjects.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class AssessmentRecordTest {

    @Test
    void shouldCreateAssessmentRecordSuccessfully(){
        //Arrange
        PatientId patientId = new PatientId(5);
        ClinicalFolderStatus status = new ClinicalFolderStatus(
                new ClinicalFolderStatusId(1),
                "ACTIVE",
                "Active status"
        );
        ClinicalFolder clinicalFolder = new ClinicalFolder(patientId, status);

        MedicalRecord medicalRecord = new MedicalRecord(clinicalFolder);

        Diagnostic diagnostic = new Diagnostic("Slight deviation in the spine and difficulty standing after 5 minutes.");
        Treatment treatment = new Treatment("6 months of therapy with a plan of 3 weekly sessions.");
        Description description = new Description("The patient is very enthusiastic about his health.");
        TherapistId therapistId = new TherapistId(1);
        AssessmentType assessmentType = AssessmentType.ASSESSMENT;
        ScheduledAt scheduledAt = new ScheduledAt(LocalDateTime.now());

        //Act
        AssessmentRecord assessmentRecord = new AssessmentRecord(
                diagnostic,
                treatment,
                description,
                therapistId,
                assessmentType,
                scheduledAt,
                medicalRecord
        );

        //Assert
        assertThat(assessmentRecord.getDiagnostic().diagnostic()).isEqualTo("Slight deviation in the spine and difficulty standing after 5 minutes.");
        assertThat(assessmentRecord.getTherapistId().value()).isEqualTo(1);
        assertThat(assessmentRecord.getAssessmentType().name()).isEqualTo("ASSESSMENT");
    }
}
