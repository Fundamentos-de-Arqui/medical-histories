package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto;

import java.time.Instant;
import java.time.LocalDateTime;

public record AssessmentRecordResponse(
        int id,
        String diagnostic,
        String treatment,
        String description,
        int therapistId,
        String assessmentType,
        LocalDateTime scheduledAt
) { }

