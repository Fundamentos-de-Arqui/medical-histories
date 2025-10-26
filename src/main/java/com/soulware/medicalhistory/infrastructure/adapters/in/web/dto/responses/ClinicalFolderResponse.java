package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.responses;

import java.time.Instant;
import java.util.List;

public record ClinicalFolderResponse(List<MedicalRecordResponse> records, String status, Instant createdAt, Instant updatedAt) {

}
