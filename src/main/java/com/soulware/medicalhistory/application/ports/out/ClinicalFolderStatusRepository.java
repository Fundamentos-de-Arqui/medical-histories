package com.soulware.medicalhistory.application.ports.out;

import com.soulware.medicalhistory.domain.model.entities.ClinicalFolderStatus;

import java.util.Optional;

public interface ClinicalFolderStatusRepository {
    Optional<ClinicalFolderStatus> findByName(String name);
}

