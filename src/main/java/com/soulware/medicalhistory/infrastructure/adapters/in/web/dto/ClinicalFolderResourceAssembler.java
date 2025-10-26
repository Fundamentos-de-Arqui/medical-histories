package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto;

import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;

public class ClinicalFolderResourceAssembler {

    public static ClinicalFolderResponse toResource(ClinicalFolder history) {
        return new ClinicalFolderResponse(
                history.getId(),
                history.getPatientId(),
                history.getStatus().getName(),
                history.getCreatedAt(),
                history.getUpdatedAt()
        );
    }
}
