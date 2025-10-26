package com.soulware.medicalhistory.domain.model.valueobjects;


import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ClinicalFolderId implements Serializable {

    private int value;

    protected ClinicalFolderId() {}

    public ClinicalFolderId(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
