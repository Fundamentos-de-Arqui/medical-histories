package com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.responses;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.ALWAYS)
public record GetAllClinicalFolderResponse(int id, String status, int patientId,  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")LocalDateTime scheduledAt){

}


