package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto;

import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.responses.GetAllClinicalFolderResponse;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.responses.MedicalRecordResponse;

import java.time.LocalDateTime;

public class GetAllClinicalFolderAssembler {
    public static GetAllClinicalFolderResponse fromDomain(ClinicalFolder folder) {
        var firstRecord = folder.getMedicalRecords()
                .stream()
                .findFirst()
                .orElse(null);

        LocalDateTime scheduledAt = null;

        if (firstRecord != null && firstRecord.getAssessmentRecord() != null) {
            scheduledAt = firstRecord.getAssessmentRecord().getScheduledAt().scheduledAt();
        }

        return new GetAllClinicalFolderResponse(
                folder.getId(),
                folder.getStatus().getName(),
                folder.getPatientId(),
                scheduledAt
        );

    }
}
