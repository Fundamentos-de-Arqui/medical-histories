package com.soulware.medicalhistory.application.ports.out;

import com.soulware.medicalhistory.domain.model.aggregates.MedicalRecord;
import com.soulware.medicalhistory.domain.queries.GetMedicalFolderByPatientIdQuery;
import com.soulware.medicalhistory.domain.queries.GetMedicalRecordByPatientAndVersionNumberQuery;

import java.util.List;


public interface MedicalRecordRepository {
    void save(MedicalRecord medicalRecord);
    MedicalRecord findMedicalRecordByPatientAndVersionNumber(GetMedicalRecordByPatientAndVersionNumberQuery query);
    List<MedicalRecord> getMedicalFolderByPatientId(GetMedicalFolderByPatientIdQuery query);
}
