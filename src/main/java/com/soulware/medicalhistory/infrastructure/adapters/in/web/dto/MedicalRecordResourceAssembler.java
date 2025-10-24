package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto;

import com.soulware.medicalhistory.domain.model.aggregates.MedicalRecord;

public class MedicalRecordResourceAssembler {
    public static MedicalRecordResponse toResource(MedicalRecord medicalRecord) {
        return new MedicalRecordResponse(
                medicalRecord.getId().value(),
                medicalRecord.getMedicalHistory().getId(),
                medicalRecord.getVersionNumber().version(),
                medicalRecord.getCreatedAt()
        );
    }
}
