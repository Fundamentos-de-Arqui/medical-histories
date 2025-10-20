package com.soulware.medicalhistory.domain.model.entities;

import com.soulware.medicalhistory.domain.model.valueobjects.MedicalHistoryStatusId;
import jakarta.persistence.*;

@Entity
@Table(name = "medical_histories_status")
public class MedicalHistoryStatus {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "medical_history_status_id"))
    private MedicalHistoryStatusId id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "description", length = 60)
    private String description;

    protected MedicalHistoryStatus() {}

    public MedicalHistoryStatus(MedicalHistoryStatusId id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public MedicalHistoryStatusId getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}