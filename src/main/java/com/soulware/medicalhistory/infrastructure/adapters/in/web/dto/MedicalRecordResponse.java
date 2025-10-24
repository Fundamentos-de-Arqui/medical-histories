package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto;

import java.time.Instant;

public record MedicalRecordResponse(
  int id,
  int medicalHistoryId,
  int versionNumber,
  Instant createdAt
)
{ }
