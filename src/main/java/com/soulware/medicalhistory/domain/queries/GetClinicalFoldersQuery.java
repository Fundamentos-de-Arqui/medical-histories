package com.soulware.medicalhistory.domain.queries;

public record GetClinicalFoldersQuery(int page, int size, String status) {
}
