package com.soulware.medicalhistory.application.ports.out;

import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;
import com.soulware.medicalhistory.domain.model.valueobjects.ClinicalFolderId;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;
import com.soulware.medicalhistory.domain.queries.GetClinicalFolderByPatientIdQuery;
import com.soulware.medicalhistory.domain.queries.GetClinicalFoldersQuery;

import java.util.List;
import java.util.Optional;

public interface ClinicalFolderRepository {
    void save(ClinicalFolder clinicalFolder);
    Optional<ClinicalFolder> findById(ClinicalFolderId id);
    Optional<ClinicalFolder> findByPatientId(PatientId patientId);
    ClinicalFolder getClinicalFolderByPatientId(GetClinicalFolderByPatientIdQuery query);
    List<ClinicalFolder> getAllClinicalFolders(GetClinicalFoldersQuery query);
    long countByStatus(String status);
}

