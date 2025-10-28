package com.soulware.medicalhistory.application.ports.in;

import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;
import com.soulware.medicalhistory.domain.queries.GetClinicalFoldersQuery;

import java.util.List;

public interface GetAllClinicalFoldersUseCase {
    List<ClinicalFolder> getAllClinicalFolders(GetClinicalFoldersQuery query);
}
