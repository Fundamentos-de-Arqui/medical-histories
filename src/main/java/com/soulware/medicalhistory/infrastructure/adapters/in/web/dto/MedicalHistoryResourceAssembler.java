package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto;

import com.soulware.medicalhistory.domain.model.aggregates.MedicalHistory;

public class MedicalHistoryResourceAssembler {

    public static MedicalHistoryResponse toResource(MedicalHistory history) {
        return new MedicalHistoryResponse(
                history.getId(),
                history.getPatientId().value(),
                history.getStatus().getName(),
                history.getCreatedAt(),
                history.getUpdatedAt()
        );
    }
}
