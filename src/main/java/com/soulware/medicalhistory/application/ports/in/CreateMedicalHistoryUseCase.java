package com.soulware.medicalhistory.application.ports.in;

import com.soulware.medicalhistory.domain.model.aggregates.MedicalHistory;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;

public interface CreateMedicalHistoryUseCase {
    MedicalHistory create(PatientId patientId);
}

