package com.soulware.medicalhistory.domain.model.aggregates;

import com.soulware.medicalhistory.domain.model.entities.ClinicalFolderStatus;
import com.soulware.medicalhistory.domain.model.valueobjects.ClinicalFolderStatusId;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MedicalRecordTest {

    @Test
    void shouldCreateMedicalRecordSuccessfully(){
        //Arrange
        PatientId patientId = new PatientId(5);
        ClinicalFolderStatus status = new ClinicalFolderStatus(
                new ClinicalFolderStatusId(1),
                "ACTIVE",
                "Active status"
        );
        ClinicalFolder clinicalFolder = new ClinicalFolder(patientId, status);

        //Act
        MedicalRecord medicalRecord = new MedicalRecord(clinicalFolder);

        //Assert
        assertThat(medicalRecord.getVersionNumber().version()).isEqualTo(1);
    }
}
