package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto;

import java.time.Instant;

public record MedicalHistoryResponse(
        int id,
        int patientId,
        String status,
        Instant createdAt,
        Instant updatedAt
) {}
