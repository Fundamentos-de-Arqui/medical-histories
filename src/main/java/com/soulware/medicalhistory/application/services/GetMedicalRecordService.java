package com.soulware.medicalhistory.application.services;

import com.soulware.medicalhistory.application.ports.in.GetMedicalRecordByPatientAndVersionNumberUseCase;
import com.soulware.medicalhistory.application.ports.out.ClinicalFolderRepository;
import com.soulware.medicalhistory.application.ports.out.MedicalRecordRepository;
import com.soulware.medicalhistory.domain.model.aggregates.MedicalRecord;
import com.soulware.medicalhistory.domain.queries.GetMedicalRecordByPatientAndVersionNumberQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class GetMedicalRecordService implements GetMedicalRecordByPatientAndVersionNumberUseCase {

    private final ClinicalFolderRepository clinicalFolderRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    @Inject
    public GetMedicalRecordService(ClinicalFolderRepository clinicalFolderRepository, MedicalRecordRepository medicalRecordRepository) {
        this.clinicalFolderRepository = clinicalFolderRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }


    
    @Override
    @Transactional
    public MedicalRecord getMedicalRecordByPatientAndVersionNumber(GetMedicalRecordByPatientAndVersionNumberQuery query) {
        return medicalRecordRepository.findMedicalRecordByPatientAndVersionNumber(query);
    }


}
