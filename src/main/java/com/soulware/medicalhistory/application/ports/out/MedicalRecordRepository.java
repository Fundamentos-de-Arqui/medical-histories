package com.soulware.medicalhistory.application.ports.out;

import com.soulware.medicalhistory.domain.model.aggregates.MedicalRecord;
import com.soulware.medicalhistory.domain.model.valueobjects.MedicalRecordId;

import java.util.Optional;

public interface MedicalRecordRepository {
    void save(MedicalRecord medicalRecord);
}
