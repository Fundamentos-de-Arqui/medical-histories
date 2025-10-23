package com.soulware.medicalhistory.domain.model.aggregates;


import com.soulware.medicalhistory.domain.model.entities.MedicalHistoryStatus;
import com.soulware.medicalhistory.domain.model.valueobjects.MedicalHistoryId;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medical_histories")
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medical_history_id")
    private int id;

    @Column(name = "patient_id", nullable = false)
    private int patientId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id")
    private MedicalHistoryStatus status;

    @OneToMany(mappedBy = "medicalHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Entry> entries = new ArrayList<>();

    @OneToMany(mappedBy = "medicalHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalRecord> medicalRecords = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    protected MedicalHistory() {}

    public MedicalHistory(PatientId patientId, MedicalHistoryStatus status) {
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

    public MedicalHistoryStatus getStatus() {
        return status;
    }

    public List<Entry> getEntries() {
        return entries;
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

