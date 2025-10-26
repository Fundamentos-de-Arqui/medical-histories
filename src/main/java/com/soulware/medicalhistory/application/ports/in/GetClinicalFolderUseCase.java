package com.soulware.medicalhistory.application.ports.in;

import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;
import com.soulware.medicalhistory.domain.model.valueobjects.ClinicalFolderId;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;

public interface GetClinicalFolderUseCase {
    ClinicalFolder getById(ClinicalFolderId id);
    ClinicalFolder getByPatientId(PatientId patientId);
}
