package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto;

public record CreateMedicalRecordWithAssessmentResponse(
        MedicalRecordResponse medicalRecord,
        AssessmentRecordResponse assessmentRecord
) { }