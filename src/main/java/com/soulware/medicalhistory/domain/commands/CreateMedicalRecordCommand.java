package com.soulware.medicalhistory.domain.commands;

import java.time.LocalDateTime;

public record CreateMedicalRecordCommand(
        int medicalHistoryId,
        String diagnostic,
        String treatment,
        String description,
        int therapistId,
        String assessmentType,
        LocalDateTime scheduledAt
) {}