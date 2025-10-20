package com.soulware.medicalhistory.infrastructure.adapters.in.web;


import com.soulware.medicalhistory.application.ports.in.CreateMedicalHistoryUseCase;
import com.soulware.medicalhistory.domain.model.aggregates.MedicalHistory;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.CreateMedicalHistoryRequest;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.MedicalHistoryResourceAssembler;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.MedicalHistoryResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;



@Path("/medical-histories")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MedicalHistoryController {

    private final CreateMedicalHistoryUseCase createMedicalHistoryUseCase;

    public MedicalHistoryController() {
        this.createMedicalHistoryUseCase = null;
    }
    @Inject
    public MedicalHistoryController(CreateMedicalHistoryUseCase createMedicalHistoryUseCase) {
        this.createMedicalHistoryUseCase = createMedicalHistoryUseCase;
    }

    @POST
    public Response createMedicalHistory(CreateMedicalHistoryRequest request) {
        MedicalHistory history = createMedicalHistoryUseCase.create(new PatientId(request.patientId()));
        MedicalHistoryResponse response = MedicalHistoryResourceAssembler.toResource(history);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/hello") // será accesible en /medical-histories/hello
    public Response helloWorld() {
        return Response.ok("{\"message\":\"Hello World\"}").build();
    }
}
