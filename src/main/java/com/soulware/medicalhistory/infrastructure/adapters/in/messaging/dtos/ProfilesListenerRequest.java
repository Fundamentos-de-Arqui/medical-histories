package com.soulware.medicalhistory.infrastructure.adapters.in.messaging.dtos;

public record ProfilesListenerRequest (
     String timestamp,
     String requestId,
     String status,
     int page_size,
     int page
){}