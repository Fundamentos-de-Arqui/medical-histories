package com.soulware.medicalhistory.application.services;

import com.soulware.medicalhistory.application.ports.in.CreateMedicalRecordUseCase;
import com.soulware.medicalhistory.application.ports.out.MedicalHistoryRepository;
import com.soulware.medicalhistory.application.ports.out.MedicalRecordRepository;
import com.soulware.medicalhistory.domain.model.aggregates.MedicalHistory;
import com.soulware.medicalhistory.domain.model.aggregates.MedicalRecord;
import com.soulware.medicalhistory.domain.model.valueobjects.MedicalHistoryId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CreateMedicalRecordService implements CreateMedicalRecordUseCase {

    private final MedicalHistoryRepository medicalHistoryRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    @Inject
    public CreateMedicalRecordService(MedicalHistoryRepository medicalHistoryRepository, MedicalRecordRepository medicalRecordRepository) {
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @Override
    @Transactional
    public MedicalRecord create(MedicalHistoryId medicalHistory) {
        MedicalHistory history = medicalHistoryRepository.findById(medicalHistory).orElseThrow(()-> new RuntimeException("Medical history not found"));

        MedicalRecord medicalRecord = new MedicalRecord(history);

        medicalRecordRepository.save(medicalRecord);
        return medicalRecord;
    }
}
