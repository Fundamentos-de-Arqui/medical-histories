package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto;

import com.soulware.medicalhistory.domain.model.aggregates.MedicalRecord;
import com.soulware.medicalhistory.domain.model.entities.AssessmentRecord;

public class MedicalRecordResourceAssembler {
    public static MedicalRecordResponse toResource(MedicalRecord medicalRecord) {
        return new MedicalRecordResponse(
                medicalRecord.getId().value(),
                medicalRecord.getMedicalHistory().getId(),
                medicalRecord.getVersionNumber().version(),
                medicalRecord.getCreatedAt()
        );
    }
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

    public static CreateMedicalRecordWithAssessmentResponse toResource(
            MedicalRecord medicalRecord,
            AssessmentRecord assessmentRecord
    ) {
        return new CreateMedicalRecordWithAssessmentResponse(
                toResource(medicalRecord),
                toResource(assessmentRecord)
        );
    }

}
