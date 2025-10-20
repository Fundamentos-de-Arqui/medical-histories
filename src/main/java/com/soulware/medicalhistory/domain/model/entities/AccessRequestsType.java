package com.soulware.medicalhistory.domain.model.entities;


import com.soulware.medicalhistory.domain.model.valueobjects.AccessRequestsTypeId;

public class AccessRequestsType {
    private final AccessRequestsTypeId id;
    private final String name;
    private final String description;

    public AccessRequestsType(AccessRequestsTypeId id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
