package com.soulware.medicalhistory.application.ports.in;

import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;
import com.soulware.medicalhistory.domain.queries.GetClinicalFolderByPatientIdQuery;

public interface GetClinicalFolderUseCase {
    ClinicalFolder getClinicalFolderByPatientId(GetClinicalFolderByPatientIdQuery query);
}
