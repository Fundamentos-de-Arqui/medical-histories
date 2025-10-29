package com.soulware.medicalhistory.domain.model.aggregates;

import com.soulware.medicalhistory.domain.model.entities.ClinicalFolderStatus;
import com.soulware.medicalhistory.domain.model.valueobjects.ClinicalFolderStatusId;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ClinicalFolderTest {

    @Test
    void shouldCreateClinicalFolderSuccessfully() {
        // Arrange
        PatientId patientId = new PatientId(5);
        ClinicalFolderStatus status = new ClinicalFolderStatus(
                new ClinicalFolderStatusId(1),
                "ACTIVE",
                "Active status"
        );

        // Act
        ClinicalFolder clinicalFolder = new ClinicalFolder(patientId, status);

        // Assert
        assertThat(clinicalFolder).isNotNull();
        assertThat(clinicalFolder.getPatientId()).isEqualTo(5);
        assertThat(clinicalFolder.getStatus().getName()).isEqualTo("ACTIVE");
    }
}
