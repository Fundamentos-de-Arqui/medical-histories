package com.soulware.medicalhistory.infrastructure.adapters.in.web;

import com.soulware.medicalhistory.application.ports.in.CreateMedicalRecordUseCase;
import com.soulware.medicalhistory.domain.model.aggregates.MedicalRecord;
import com.soulware.medicalhistory.domain.model.valueobjects.MedicalHistoryId;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.CreateMedicalRecordRequest;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.MedicalRecordResourceAssembler;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.MedicalRecordResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/medical-records")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MedicalRecordController {
    private final CreateMedicalRecordUseCase createMedicalRecordUseCase;

    public MedicalRecordController(){
        this.createMedicalRecordUseCase = null;
    }

    @Inject
    public MedicalRecordController(CreateMedicalRecordUseCase createMedicalRecordUseCase){
        this.createMedicalRecordUseCase = createMedicalRecordUseCase;
    }

    @POST
    public Response createMedicalRecord(CreateMedicalRecordRequest request) {
        assert createMedicalRecordUseCase != null;
        MedicalRecord medicalRecord = createMedicalRecordUseCase.create(new MedicalHistoryId(request.medicalHistoryId()));
        MedicalRecordResponse medicalRecordResponse = MedicalRecordResourceAssembler.toResource(medicalRecord);

        return Response.status(Response.Status.CREATED).entity(medicalRecordResponse).build();

    }

    @GET
    @Path("/hello")
    public Response helloWorld() {
        return Response.ok("{\"message\":\"Hello World\"}").build();
    }

}
