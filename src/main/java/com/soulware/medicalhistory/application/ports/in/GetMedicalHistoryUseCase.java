package com.soulware.medicalhistory.application.ports.in;

import com.soulware.medicalhistory.domain.model.aggregates.MedicalHistory;
import com.soulware.medicalhistory.domain.model.valueobjects.MedicalHistoryId;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;

import java.util.Optional;

public interface GetMedicalHistoryUseCase {
    MedicalHistory getById(MedicalHistoryId id);
    MedicalHistory getByPatientId(PatientId patientId);
}
