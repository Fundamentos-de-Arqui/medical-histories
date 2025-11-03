package com.soulware.medicalhistory.application.services;

import com.soulware.medicalhistory.application.ports.in.GetAllClinicalFoldersUseCase;
import com.soulware.medicalhistory.application.ports.in.GetClinicalFolderUseCase;
import com.soulware.medicalhistory.application.ports.out.ClinicalFolderRepository;
import com.soulware.medicalhistory.application.results.PagedClinicalFoldersResult;
import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;
import com.soulware.medicalhistory.domain.model.valueobjects.ClinicalFolderStatus;
import com.soulware.medicalhistory.domain.queries.GetClinicalFolderByPatientIdQuery;
import com.soulware.medicalhistory.domain.queries.GetClinicalFoldersQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ClinicalFolderQueryService implements GetClinicalFolderUseCase, GetAllClinicalFoldersUseCase {

    private final ClinicalFolderRepository clinicalFolderRepository;

    @Inject
    public ClinicalFolderQueryService(ClinicalFolderRepository clinicalFolderRepository) {
        this.clinicalFolderRepository = clinicalFolderRepository;
    }

    @Override
    @Transactional
    public ClinicalFolder getClinicalFolderByPatientId(GetClinicalFolderByPatientIdQuery query) {
        return clinicalFolderRepository.getClinicalFolderByPatientId(query);
    }

    @Override
    @Transactional
    public PagedClinicalFoldersResult getAllClinicalFolders(GetClinicalFoldersQuery query) {

        var statusOpt = ClinicalFolderStatus.fromString(query.status());

        if (statusOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid status value: " + query.status());
        }

        var folders = clinicalFolderRepository.getAllClinicalFolders(query);
        long totalElements = clinicalFolderRepository.countByStatus(query.status());
        int totalPages = (int) Math.ceil((double) totalElements / query.size());

        return new PagedClinicalFoldersResult(
                folders,
                totalElements,
                totalPages,
                query.page(),
                query.size()
        );
    }
}
