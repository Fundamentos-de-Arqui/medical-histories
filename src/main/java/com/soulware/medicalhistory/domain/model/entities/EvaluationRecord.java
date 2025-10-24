package com.soulware.medicalhistory.domain.model.entities;

import com.soulware.medicalhistory.domain.model.aggregates.MedicalRecord;
import com.soulware.medicalhistory.domain.model.valueobjects.*;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "assessment_record")
public class EvaluationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assessment_record_id")
    private int id;

    @Column(name = "diagnostic", nullable = false, length = 100)
    private String diagnostic;

    @Column(name = "treatment", nullable = false, length = 100)
    private String treatment;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @Column(name = "therapist_id", nullable = false)
    private int therapistId;

    @Enumerated(EnumType.STRING)
    @Column(name = "assessment_type", nullable = false, length = 20)
    private AssessmentType assessmentType;

    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medical_record_id", referencedColumnName = "medical_record_id")
    private MedicalRecord medicalRecord;


    protected EvaluationRecord() {}

    public EvaluationRecord(Diagnostic diagnostic, Treatment treatment, Description description, TherapistId therapistId, AssessmentType assessmentType, ScheduledAt scheduledAt) {
        this.diagnostic = diagnostic.diagnostic();
        this.treatment = treatment.treatment();
        this.description = description.description();
        this.therapistId = therapistId.value();
        this.assessmentType = assessmentType;
        this.scheduledAt = scheduledAt.scheduledAt();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public EvaluationRecordId getId() {
        return new EvaluationRecordId(id);
    }




}
