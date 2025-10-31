package com.soulware.medicalhistory.application.ports.in;

import com.soulware.medicalhistory.application.results.PagedClinicalFoldersResult;
import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;
import com.soulware.medicalhistory.domain.queries.GetClinicalFoldersQuery;

import java.util.List;

public interface GetAllClinicalFoldersUseCase {
    PagedClinicalFoldersResult getAllClinicalFolders(GetClinicalFoldersQuery query);
}
