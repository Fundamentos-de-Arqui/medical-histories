package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.requests;

public record GetPagedAllClinicalFoldersRequest(
        int page,
        int size,
        String status
) { }