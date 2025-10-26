package com.soulware.medicalhistory.domain.model.entities;

import com.soulware.medicalhistory.domain.model.valueobjects.ClinicalFolderStatusId;
import jakarta.persistence.*;

@Entity
@Table(name = "clinical_folder_status")
public class ClinicalFolderStatus {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "clinical_folder_status_id"))
    private ClinicalFolderStatusId id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "description", length = 60)
    private String description;

    protected ClinicalFolderStatus() {}

    public ClinicalFolderStatus(ClinicalFolderStatusId id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public ClinicalFolderStatusId getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}