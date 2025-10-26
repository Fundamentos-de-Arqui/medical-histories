package com.soulware.medicalhistory.application.ports.in;

import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;
import com.soulware.medicalhistory.domain.queries.GetMedicalFolderByPatientIdQuery;

import java.util.List;

public interface GetClinicalFolderUseCase {
    ClinicalFolder getClinicalFolderByPatientId(GetMedicalFolderByPatientIdQuery query);
}
