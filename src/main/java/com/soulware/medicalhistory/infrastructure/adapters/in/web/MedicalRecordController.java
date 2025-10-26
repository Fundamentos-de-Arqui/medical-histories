package com.soulware.medicalhistory.infrastructure.adapters.in.web;

import com.soulware.medicalhistory.application.ports.in.CreateMedicalRecordUseCase;
import com.soulware.medicalhistory.domain.commands.CreateMedicalRecordCommand;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.*;
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
        var command = new CreateMedicalRecordCommand(
                request.clinicalFolderId(),
                request.diagnostic(),
                request.treatment(),
                request.description(),
                request.therapistId(),
                request.assessmentType(),
                request.scheduledAt()
        );

        assert createMedicalRecordUseCase != null;
        var result = createMedicalRecordUseCase.create(command);

        var response = MedicalRecordResourceAssembler.toResource(
                result.medicalRecord(),
                result.assessmentRecord()
        );

        return Response.status(Response.Status.CREATED).entity(response).build();

    }

}
