package com.soulware.medicalhistory.application.services;

import com.soulware.medicalhistory.application.ports.in.CreateClinicalFolderUseCase;
import com.soulware.medicalhistory.application.ports.out.ClinicalFolderRepository;
import com.soulware.medicalhistory.application.ports.out.ClinicalFolderStatusRepository;
import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;
import com.soulware.medicalhistory.domain.model.entities.ClinicalFolderStatus;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CreateClinicalFolderService implements CreateClinicalFolderUseCase {

    private final ClinicalFolderRepository repository;
    private final ClinicalFolderStatusRepository statusRepository;

    @Inject
    public CreateClinicalFolderService(ClinicalFolderRepository repository,
                                       ClinicalFolderStatusRepository statusRepository) {
        this.repository = repository;
        this.statusRepository = statusRepository;
    }

    @Override
    @Transactional
    public ClinicalFolder create(PatientId patientId) {
        ClinicalFolderStatus activeStatus = statusRepository
                .findByName("ACTIVE")
                .orElseThrow(() -> new IllegalStateException("ACTIVE status not found"));

       ClinicalFolder history = new ClinicalFolder(
                patientId,
                activeStatus
        );
        repository.save(history);
        return history;
    }
}
