package com.soulware.medicalhistory.application.services;

import com.soulware.medicalhistory.application.ports.in.GetMedicalHistoryUseCase;
import com.soulware.medicalhistory.application.ports.out.MedicalHistoryRepository;
import com.soulware.medicalhistory.application.ports.out.MedicalHistoryStatusRepository;
import com.soulware.medicalhistory.domain.model.aggregates.MedicalHistory;
import com.soulware.medicalhistory.domain.model.entities.MedicalHistoryStatus;
import com.soulware.medicalhistory.domain.model.valueobjects.MedicalHistoryId;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class GetMedicalHistoryService implements GetMedicalHistoryUseCase {
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final MedicalHistoryStatusRepository medicalHistoryStatusRepository;

    @Inject
    public GetMedicalHistoryService(MedicalHistoryRepository medicalHistoryRepository, MedicalHistoryStatusRepository medicalHistoryStatusRepository) {
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.medicalHistoryStatusRepository = medicalHistoryStatusRepository;
    }

    @Override
    @Transactional
    public MedicalHistory getById(MedicalHistoryId id) {
        return medicalHistoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Medical history not found"));
    }

    @Override
    @Transactional
    public MedicalHistory getByPatientId(PatientId patientId) {
        return medicalHistoryRepository.findByPatientId(patientId).orElseThrow(()-> new RuntimeException("Medical history not found"));
    }


}
