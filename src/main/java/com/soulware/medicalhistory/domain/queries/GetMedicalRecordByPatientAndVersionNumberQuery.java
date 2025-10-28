package com.soulware.medicalhistory.domain.queries;

public record GetMedicalRecordByPatientAndVersionNumberQuery(Integer patient, Integer versionNumber) {
}
