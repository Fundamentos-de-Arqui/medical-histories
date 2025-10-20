package com.soulware.medicalhistory.domain.model.aggregates;

import com.soulware.medicalhistory.domain.model.valueobjects.EntryId;
import com.soulware.medicalhistory.domain.model.valueobjects.TherapistId;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Entry {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "entry_id"))
    private EntryId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "therapist_id"))
    private TherapistId therapistId;

    private String content;
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_history_id")
    private MedicalHistory medicalHistory;

    public Entry(EntryId id, TherapistId therapistId, String content) {
        this.id = id;
        this.therapistId = therapistId;
        this.content = content;
        this.createdAt = Instant.now();
    }

    public Entry() {}

    public void updateContent(String newContent) {
        this.content = newContent;
    }

    public EntryId getId() { return id; }
    public TherapistId getTherapistId() { return therapistId; }
    public String getContent() { return content; }
    public Instant getCreatedAt() { return createdAt; }
}
