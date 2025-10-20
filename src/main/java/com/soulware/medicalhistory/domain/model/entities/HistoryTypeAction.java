package com.soulware.medicalhistory.domain.model.entities;

import com.soulware.medicalhistory.domain.model.valueobjects.HistoryTypeActionId;

public class HistoryTypeAction {
    private final HistoryTypeActionId id;
    private final String name;
    private final String description;

    public HistoryTypeAction(HistoryTypeActionId id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}

