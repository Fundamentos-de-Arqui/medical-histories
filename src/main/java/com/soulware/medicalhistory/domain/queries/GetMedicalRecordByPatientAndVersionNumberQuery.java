package com.soulware.medicalhistory.domain.queries;

public record GetMedicalRecordByPatientAndVersionNumberQuery(int patient, int versionNumber) {
}
