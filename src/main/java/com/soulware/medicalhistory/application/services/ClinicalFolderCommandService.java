package com.soulware.medicalhistory.application.services;

import com.soulware.medicalhistory.application.ports.in.CreateClinicalFolderUseCase;
import com.soulware.medicalhistory.application.ports.out.ClinicalFolderRepository;
import com.soulware.medicalhistory.application.ports.out.ClinicalFolderStatusRepository;
import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;
import com.soulware.medicalhistory.domain.model.entities.ClinicalFolderStatus;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ClinicalFolderCommandService implements CreateClinicalFolderUseCase {

    private final ClinicalFolderRepository repository;
    private final ClinicalFolderStatusRepository statusRepository;

    @Inject
    public ClinicalFolderCommandService(ClinicalFolderRepository repository,
                                        ClinicalFolderStatusRepository statusRepository) {
        this.repository = repository;
        this.statusRepository = statusRepository;
    }

    @Override
    @Transactional
    public ClinicalFolder create(PatientId patientId) {
        //v erifica si ya hay un folder relacionado a un paciente
        repository.findByPatientId(patientId).orElseThrow(()-> new EntityNotFoundException("clinical folder with patient id " + patientId + " not found"));

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
