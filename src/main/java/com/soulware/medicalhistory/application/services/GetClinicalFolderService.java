package com.soulware.medicalhistory.application.services;

import com.soulware.medicalhistory.application.ports.in.GetClinicalFolderUseCase;
import com.soulware.medicalhistory.application.ports.out.ClinicalFolderRepository;
import com.soulware.medicalhistory.application.ports.out.ClinicalFolderStatusRepository;
import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;
import com.soulware.medicalhistory.domain.model.valueobjects.ClinicalFolderId;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class GetClinicalFolderService implements GetClinicalFolderUseCase {
    private final ClinicalFolderRepository clinicalFolderRepository;
    private final ClinicalFolderStatusRepository clinicalFolderStatusRepository;

    @Inject
    public GetClinicalFolderService(ClinicalFolderRepository clinicalFolderRepository, ClinicalFolderStatusRepository clinicalFolderStatusRepository) {
        this.clinicalFolderRepository = clinicalFolderRepository;
        this.clinicalFolderStatusRepository = clinicalFolderStatusRepository;
    }

    @Override
    @Transactional
    public ClinicalFolder getById(ClinicalFolderId id) {
        return clinicalFolderRepository.findById(id).orElseThrow(()-> new RuntimeException("Clinical Folder not found"));
    }

    @Override
    @Transactional
    public ClinicalFolder getByPatientId(PatientId patientId) {
        return clinicalFolderRepository.findByPatientId(patientId).orElseThrow(()-> new RuntimeException("Clinical Folder not found"));
    }


}
