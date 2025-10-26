package com.soulware.medicalhistory.application.ports.in;

import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;

public interface CreateClinicalFolderUseCase {
    ClinicalFolder create(PatientId patientId);
}

