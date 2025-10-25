package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto;

import com.soulware.medicalhistory.domain.model.entities.AssessmentRecord;

public class AssessmentRecordResourceAssembler {
    public static AssessmentRecordResponse toResource(AssessmentRecord assessmentRecord) {
        return new AssessmentRecordResponse(
                assessmentRecord.getId().id(),
                assessmentRecord.getDiagnostic().diagnostic(),
                assessmentRecord.getTreatment().treatment(),
                assessmentRecord.getDescription().description(),
                assessmentRecord.getTherapistId().value(),
                assessmentRecord.getAssessmentType().name(),
                assessmentRecord.getScheduledAt().scheduledAt()
        );
    }
}
