package com.soulware.medicalhistory.application.ports.out;

import com.soulware.medicalhistory.domain.model.entities.MedicalHistoryStatus;

import java.util.Optional;

public interface MedicalHistoryStatusRepository {
    Optional<MedicalHistoryStatus> findByName(String name);
}

