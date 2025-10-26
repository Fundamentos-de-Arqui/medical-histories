package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.responses;

import java.time.Instant;
import java.time.LocalDateTime;

public record MedicalRecordResponse(int id,
                                    String diagnostic,
                                    String treatment,
                                    String description,
                                    String assessmentType,
                                    int therapistId,
                                    int versionNumber,
                                    LocalDateTime scheduledAt,
                                    Instant createdAt) {
}
