package com.soulware.medicalhistory.domain.model.aggregates;

import com.soulware.medicalhistory.domain.model.entities.AssessmentRecord;
import com.soulware.medicalhistory.domain.model.valueobjects.MedicalRecordId;
import com.soulware.medicalhistory.domain.model.valueobjects.VersionNumber;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "medical_record")
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medical_record_id")
    private int id;

    @Column(name = "version_number", nullable = false)
    private int versionNumber;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clinical_folder_id")
    private ClinicalFolder clinicalFolder;

    @OneToOne(mappedBy = "medicalRecord", fetch = FetchType.LAZY)
    private AssessmentRecord assessmentRecord;


    protected MedicalRecord() {}

    public MedicalRecord(ClinicalFolder clinicalFolder) {
        this.versionNumber = clinicalFolder.getNextVersionNumber();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.clinicalFolder = clinicalFolder;


    }

    public VersionNumber getVersionNumber() {
        return new VersionNumber(versionNumber);
    }

    public void setVersionNumber(VersionNumber versionNumber) {
        this.versionNumber = versionNumber.version();
    }

    public MedicalRecordId getId(){
        return new MedicalRecordId(id);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public ClinicalFolder getClinicalFolder() {
        return clinicalFolder;
    }

    public AssessmentRecord getAssessmentRecord() {
        return assessmentRecord;
    }

}
