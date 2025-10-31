package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.responses;

import java.util.List;

public record PagedClinicalFoldersResponse(List<GetAllClinicalFolderResponse> folders,
                                           int totalPages,
                                           long totalElements,
                                           int currentPage,
                                           int pageSize) {
}
