package com.soulware.medicalhistory.application.services;

import com.soulware.medicalhistory.application.ports.in.CreateMedicalHistoryUseCase;
import com.soulware.medicalhistory.application.ports.out.MedicalHistoryRepository;
import com.soulware.medicalhistory.application.ports.out.MedicalHistoryStatusRepository;
import com.soulware.medicalhistory.domain.model.aggregates.MedicalHistory;
import com.soulware.medicalhistory.domain.model.entities.MedicalHistoryStatus;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CreateMedicalHistoryService implements CreateMedicalHistoryUseCase {

    private final MedicalHistoryRepository repository;
    private final MedicalHistoryStatusRepository statusRepository;

    @Inject
    public CreateMedicalHistoryService(MedicalHistoryRepository repository,
                                       MedicalHistoryStatusRepository statusRepository) {
        this.repository = repository;
        this.statusRepository = statusRepository;
    }

    @Override
    @Transactional
    public MedicalHistory create(PatientId patientId) {
        MedicalHistoryStatus activeStatus = statusRepository
                .findByName("ACTIVE")
                .orElseThrow(() -> new IllegalStateException("ACTIVE status not found"));

        MedicalHistory history = new MedicalHistory(
                patientId,
                activeStatus
        );
        repository.save(history);
        return history;
    }
}
