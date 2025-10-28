package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.requests;

public record GetMedicalRecordByVersionNumberRequest(Integer patientId, Integer versionNumber, String orderBy) {
    public GetMedicalRecordByVersionNumberRequest {

        if (patientId == null) {
            throw new IllegalArgumentException("patientId must not be null");
        }
        if (patientId <= 0) {
            throw new IllegalArgumentException("patientId must be greater than 0");
        }
        if (versionNumber != null && versionNumber <= 0) {
            throw new IllegalArgumentException("versionNumber must be greater than 0 if provided");
        }
        if (orderBy == null) {
            throw new IllegalArgumentException("orderBy must not be null");
        }

    }
}
