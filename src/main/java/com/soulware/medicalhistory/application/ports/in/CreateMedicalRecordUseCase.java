package com.soulware.medicalhistory.application.ports.in;

import com.soulware.medicalhistory.application.results.CreatedMedicalRecordResult;
import com.soulware.medicalhistory.domain.commands.CreateMedicalRecordCommand;

public interface CreateMedicalRecordUseCase {
    CreatedMedicalRecordResult create(CreateMedicalRecordCommand command);

}
