package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.responses;

import java.time.Instant;

public record MedicalRecordResponse(int id,
                                    String diagnostic,
                                    String treatment,
                                    Instant createdAt) {
}
