package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto;

public record MedicalHistoryResponse(
        int id,
        int patientId,
        String status,
        String createdAt,
        String updatedAt
) {}
