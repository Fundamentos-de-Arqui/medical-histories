package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.responses;

import java.util.List;

public record ClinicalFolderResponse(int patientId, List<MedicalRecordResponse> records) {

}
