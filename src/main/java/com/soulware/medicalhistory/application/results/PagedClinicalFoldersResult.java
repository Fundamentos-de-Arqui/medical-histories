package com.soulware.medicalhistory.application.results;

import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;

import java.util.List;

public record PagedClinicalFoldersResult(
        List<ClinicalFolder> folders,
        long totalElements,
        int totalPages,
        int currentPage,
        int pageSize
) { }
