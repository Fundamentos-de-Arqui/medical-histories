package com.soulware.medicalhistory.application.results;

import com.soulware.medicalhistory.domain.model.aggregates.MedicalRecord;
import com.soulware.medicalhistory.domain.model.entities.AssessmentRecord;

public record CreatedMedicalRecordResult(MedicalRecord medicalRecord, AssessmentRecord assessmentRecord) {}
