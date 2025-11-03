package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.time.LocalDateTime;

public record MedicalRecordResponse(int id,
                                    String diagnostic,
                                    String treatment,
                                    String description,
                                    String assessmentType,
                                    int therapistId,
                                    int versionNumber,
                                    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime scheduledAt,
                                    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")Instant createdAt) {
}
