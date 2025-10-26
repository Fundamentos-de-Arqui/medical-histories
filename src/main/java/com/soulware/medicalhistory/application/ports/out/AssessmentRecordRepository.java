package com.soulware.medicalhistory.application.ports.out;

import com.soulware.medicalhistory.domain.model.entities.AssessmentRecord;

public interface AssessmentRecordRepository {
    void save(AssessmentRecord record);
}
