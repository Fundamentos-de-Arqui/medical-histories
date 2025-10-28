package com.soulware.medicalhistory.domain.model.valueobjects;

import java.util.Arrays;
import java.util.Optional;

public enum ClinicalFolderStatus {
    ACTIVE, INACTIVE, ARCHIVED;

    public static Optional<ClinicalFolderStatus> fromString(String value) {
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(value))
                .findFirst();
    }
}
