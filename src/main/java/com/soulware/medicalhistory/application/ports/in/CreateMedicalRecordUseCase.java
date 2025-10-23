package com.soulware.medicalhistory.application.ports.in;

import com.soulware.medicalhistory.domain.model.aggregates.MedicalHistory;
import com.soulware.medicalhistory.domain.model.aggregates.MedicalRecord;
import com.soulware.medicalhistory.domain.model.valueobjects.MedicalHistoryId;

public interface CreateMedicalRecordUseCase {
    MedicalRecord create(MedicalHistoryId medicalHistoryId);

}
