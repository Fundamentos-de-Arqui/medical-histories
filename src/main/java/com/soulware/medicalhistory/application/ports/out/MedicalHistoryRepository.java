package com.soulware.medicalhistory.application.ports.out;

import com.soulware.medicalhistory.domain.model.aggregates.MedicalHistory;
import com.soulware.medicalhistory.domain.model.valueobjects.MedicalHistoryId;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;

import java.util.Optional;

public interface MedicalHistoryRepository {
    void save(MedicalHistory medicalHistory);
    Optional<MedicalHistory> findById(MedicalHistoryId id);
    Optional<MedicalHistory> findByPatientId(PatientId patientId);
}

