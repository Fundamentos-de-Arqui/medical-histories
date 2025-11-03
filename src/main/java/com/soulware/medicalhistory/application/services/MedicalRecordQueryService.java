package com.soulware.medicalhistory.application.services;

import com.soulware.medicalhistory.application.ports.in.GetMedicalRecordByPatientAndVersionNumberUseCase;
import com.soulware.medicalhistory.application.ports.out.MedicalRecordRepository;
import com.soulware.medicalhistory.domain.model.aggregates.MedicalRecord;
import com.soulware.medicalhistory.domain.queries.GetMedicalRecordByPatientAndVersionNumberQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MedicalRecordQueryService implements GetMedicalRecordByPatientAndVersionNumberUseCase {

    private final MedicalRecordRepository medicalRecordRepository;

    @Inject
    public MedicalRecordQueryService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }
    
    @Override
    @Transactional
    public MedicalRecord getMedicalRecordByPatientAndVersionNumber(GetMedicalRecordByPatientAndVersionNumberQuery query) {
        if (query.versionNumber() == null) {
            return medicalRecordRepository.findLatestMedicalRecordByPatient(query.patient());
        }

        return medicalRecordRepository.findMedicalRecordByPatientAndVersionNumber(query);
    }

}
