package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soulware.medicalhistory.domain.model.aggregates.MedicalRecord;
import com.soulware.medicalhistory.domain.model.entities.AssessmentRecord;

import java.time.Instant;
import java.time.LocalDateTime;

public record MedicalRecordDetailResponse(
        int id,
    int versionNumber,
    String diagnostic,
    String treatment,
    String description,
    int therapistId,
    String assessmentType,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime scheduledAt,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")Instant createdAt
) {

public static MedicalRecordDetailResponse from(MedicalRecord record) {
    AssessmentRecord assessment = record.getAssessmentRecord();

    return new MedicalRecordDetailResponse(
            record.getId().value(),
            record.getVersionNumber().version(),
            assessment.getDiagnostic().diagnostic(),
            assessment.getTreatment().treatment(),
            assessment.getDescription().description(),
            assessment.getTherapistId().value(),
            assessment.getAssessmentType().name(),
            assessment.getScheduledAt().scheduledAt(),
            assessment.getCreatedAt()
    );
}
}
