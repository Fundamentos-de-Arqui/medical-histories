package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto;

import java.time.LocalDateTime;

public record CreateMedicalRecordRequest(
        int clinicalFolderId,
        String diagnostic,
        String treatment,
        String description,
        int therapistId,
        String assessmentType,
        LocalDateTime scheduledAt
) { }