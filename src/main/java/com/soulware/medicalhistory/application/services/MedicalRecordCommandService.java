package com.soulware.medicalhistory.application.services;

import com.soulware.medicalhistory.application.ports.in.CreateMedicalRecordUseCase;
import com.soulware.medicalhistory.application.ports.out.AssessmentRecordRepository;
import com.soulware.medicalhistory.application.ports.out.ClinicalFolderRepository;
import com.soulware.medicalhistory.application.ports.out.MedicalRecordRepository;
import com.soulware.medicalhistory.application.results.CreatedMedicalRecordResult;
import com.soulware.medicalhistory.domain.commands.CreateMedicalRecordCommand;
import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;
import com.soulware.medicalhistory.domain.model.aggregates.MedicalRecord;
import com.soulware.medicalhistory.domain.model.entities.AssessmentRecord;
import com.soulware.medicalhistory.domain.model.valueobjects.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MedicalRecordCommandService implements CreateMedicalRecordUseCase {

    private final ClinicalFolderRepository clinicalFolderRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final AssessmentRecordRepository assessmentRecordRepository;

    @Inject
    public MedicalRecordCommandService(ClinicalFolderRepository clinicalFolderRepository, MedicalRecordRepository medicalRecordRepository, AssessmentRecordRepository assessmentRecordRepository) {
        this.clinicalFolderRepository = clinicalFolderRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.assessmentRecordRepository = assessmentRecordRepository;
    }

    @Override
    @Transactional
    public CreatedMedicalRecordResult create(CreateMedicalRecordCommand command) {

        var medicalHistoryId = new ClinicalFolderId(command.clinicalFolderId());
        var diagnostic = new Diagnostic(command.diagnostic());
        var treatment = new Treatment(command.treatment());
        var description = new Description(command.description());
        var therapistId = new TherapistId(command.therapistId());
        var assessmentType = AssessmentType.valueOf(command.assessmentType());
        var scheduledAt = new ScheduledAt(command.scheduledAt());

        ClinicalFolder history = clinicalFolderRepository.findById(medicalHistoryId).orElseThrow(()-> new RuntimeException("Medical history not found"));

        MedicalRecord medicalRecord = new MedicalRecord(history);

        medicalRecordRepository.save(medicalRecord);

        AssessmentRecord assessmentRecord = new AssessmentRecord(diagnostic ,treatment, description, therapistId, assessmentType, scheduledAt, medicalRecord);

        assessmentRecordRepository.save(assessmentRecord);

        return new CreatedMedicalRecordResult(medicalRecord, assessmentRecord);
    }
}
