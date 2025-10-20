package com.soulware.medicalhistory.domain.model.aggregates;

import com.soulware.medicalhistory.domain.model.entities.AccessRequestsType;
import com.soulware.medicalhistory.domain.model.valueobjects.AccessRequestId;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;
import com.soulware.medicalhistory.domain.model.valueobjects.TherapistId;

import java.time.Instant;

public class AccessRequest {
    private final AccessRequestId id;
    private final TherapistId therapistId;
    private final PatientId patientId;
    private AccessRequestsType status;
    private final Instant requestedAt;
    private Instant approvedUntil;

    public AccessRequest(AccessRequestId id, TherapistId therapistId, PatientId patientId) {
        this.id = id;
        this.therapistId = therapistId;
        this.patientId = patientId;
        this.status = null;
        this.requestedAt = Instant.now();
    }

    public void approve(Instant until) {
        this.status = null;
        this.approvedUntil = until;
    }

    public void reject() {
        this.status = null;
    }

    // --- Getters ---
    public AccessRequestId getId() { return id; }
    public TherapistId getTherapistId() { return therapistId; }
    public PatientId getPatientId() { return patientId; }
    public AccessRequestsType getStatus() { return status; }
    public Instant getRequestedAt() { return requestedAt; }
    public Instant getApprovedUntil() { return approvedUntil; }
}

