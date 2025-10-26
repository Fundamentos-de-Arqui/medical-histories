package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto;

import com.soulware.medicalhistory.domain.model.aggregates.MedicalRecord;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.responses.ClinicalFolderResponse;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.responses.MedicalRecordResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ClinicalFolderAssembler {

    public static ClinicalFolderResponse toResponse(int patientId, List<MedicalRecord> records) {
        List<MedicalRecordResponse> recordResponses = records.stream()
                .map(r -> new MedicalRecordResponse(
                        r.getId().value(),
                        r.getAssessmentRecord().getDiagnostic().diagnostic(),
                        r.getAssessmentRecord().getTreatment().treatment(),
                        r.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return new ClinicalFolderResponse(patientId, recordResponses);
    }
}