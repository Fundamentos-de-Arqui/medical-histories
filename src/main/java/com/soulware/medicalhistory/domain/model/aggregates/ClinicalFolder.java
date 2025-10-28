package com.soulware.medicalhistory.domain.model.aggregates;


import com.soulware.medicalhistory.domain.model.entities.ClinicalFolderStatus;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clinical_folder")
public class ClinicalFolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clinical_folder_id")
    private int id;

    @Column(name = "patient_id", nullable = false, unique = true)
    private int patientId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id")
    private ClinicalFolderStatus status;

    @OneToMany(mappedBy = "clinicalFolder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Entry> entries = new ArrayList<>();

    @OneToMany(mappedBy = "clinicalFolder", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("versionNumber DESC")
    private List<MedicalRecord> medicalRecords = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    protected ClinicalFolder() {}

    public ClinicalFolder(PatientId patientId, ClinicalFolderStatus status) {
        this.patientId = patientId.value();
        this.status = status;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void addEntry(Entry entry) {
        entries.add(entry);
        this.updatedAt = Instant.now();
    }

    public void addMedicalRecord(MedicalRecord record) {
        medicalRecords.add(record);
        this.updatedAt = Instant.now();
    }

    public void updateEntry(Entry entry) {
        entries.removeIf(e -> e.getId().equals(entry.getId()));
        entries.add(entry);
        this.updatedAt = Instant.now();
    }

    public int getId() {
        return id;
    }

    public int getPatientId() {
        return patientId;
    }

    public ClinicalFolderStatus getStatus() {
        return status;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public int getNextVersionNumber() {
        return (medicalRecords == null ? 0 : medicalRecords.size()) + 1;
    }

}

