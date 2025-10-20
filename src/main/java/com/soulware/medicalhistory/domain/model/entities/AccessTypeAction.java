package com.soulware.medicalhistory.domain.model.entities;


import com.soulware.medicalhistory.domain.model.valueobjects.AccessTypeActionId;

public class AccessTypeAction {
    private final AccessTypeActionId id;
    private final String name;
    private final String description;

    public AccessTypeAction(AccessTypeActionId id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}

