package com.soulware.medicalhistory.application.services;

import com.soulware.medicalhistory.application.ports.in.GetClinicalFolderUseCase;
import com.soulware.medicalhistory.application.ports.out.ClinicalFolderRepository;
import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;
import com.soulware.medicalhistory.domain.queries.GetMedicalFolderByPatientIdQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ClinicalFolderQueryService implements GetClinicalFolderUseCase {

    private final ClinicalFolderRepository clinicalFolderRepository;

    @Inject
    public ClinicalFolderQueryService(ClinicalFolderRepository clinicalFolderRepository) {
        this.clinicalFolderRepository = clinicalFolderRepository;
    }

    @Override
    @Transactional
    public ClinicalFolder getClinicalFolderByPatientId(GetMedicalFolderByPatientIdQuery query) {
        return clinicalFolderRepository.getClinicalFolderByPatientId(query);
    }
}
