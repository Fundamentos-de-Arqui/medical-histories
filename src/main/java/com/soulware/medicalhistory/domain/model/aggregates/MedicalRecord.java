package com.soulware.medicalhistory.domain.model.aggregates;

import com.soulware.medicalhistory.domain.model.entities.EvaluationRecord;
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
    @JoinColumn(name = "medical_history_id")
    private MedicalHistory medicalHistory;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "evaluation_record_id", referencedColumnName = "evaluation_record_id")
    private EvaluationRecord evaluationRecord;


    protected MedicalRecord() {}

    public MedicalRecord(MedicalHistory medicalHistory) {
        this.versionNumber = medicalHistory.getNextVersionNumber();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.medicalHistory = medicalHistory;


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




}
