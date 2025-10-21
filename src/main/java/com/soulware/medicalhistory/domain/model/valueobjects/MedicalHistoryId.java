package com.soulware.medicalhistory.domain.model.valueobjects;


import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MedicalHistoryId implements Serializable {

    private int value;

    protected MedicalHistoryId() {}

    public MedicalHistoryId(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
