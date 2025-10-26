package com.soulware.medicalhistory.application.ports.in;

import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;
import com.soulware.medicalhistory.domain.model.aggregates.MedicalRecord;
import com.soulware.medicalhistory.domain.model.valueobjects.ClinicalFolderId;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;
import com.soulware.medicalhistory.domain.queries.GetMedicalFolderByPatientIdQuery;

import java.util.List;

public interface GetClinicalFolderUseCase {
    List<MedicalRecord> getClinicalFolderByPatientId(GetMedicalFolderByPatientIdQuery query);
}
