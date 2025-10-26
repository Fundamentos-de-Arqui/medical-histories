package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto;

import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.responses.ClinicalFolderResponse;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.responses.MedicalRecordResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ClinicalFolderAssembler {

    public static ClinicalFolderResponse toResponse(ClinicalFolder folder) {
        List<MedicalRecordResponse> recordResponses = folder.getMedicalRecords().stream()
                .map(r -> new MedicalRecordResponse(
                        r.getId().value(),
                        r.getAssessmentRecord().getDiagnostic().diagnostic(),
                        r.getAssessmentRecord().getTreatment().treatment(),
                        r.getAssessmentRecord().getDescription().description(),
                        r.getAssessmentRecord().getAssessmentType().name(),
                        r.getAssessmentRecord().getTherapistId().value(),
                        r.getVersionNumber().version(),
                        r.getAssessmentRecord().getScheduledAt().scheduledAt(),
                        r.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return new ClinicalFolderResponse(
                recordResponses,
                folder.getStatus().getName(),
                folder.getCreatedAt(),
                folder.getUpdatedAt()
        );
    }
}