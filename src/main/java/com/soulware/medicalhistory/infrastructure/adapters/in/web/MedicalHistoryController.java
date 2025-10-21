package com.soulware.medicalhistory.infrastructure.adapters.in.web;


import com.soulware.medicalhistory.application.ports.in.CreateMedicalHistoryUseCase;
import com.soulware.medicalhistory.application.ports.in.GetMedicalHistoryUseCase;
import com.soulware.medicalhistory.domain.model.aggregates.MedicalHistory;
import com.soulware.medicalhistory.domain.model.valueobjects.MedicalHistoryId;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.CreateMedicalHistoryRequest;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.MedicalHistoryResourceAssembler;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.MedicalHistoryResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Optional;


@Path("/medical-histories")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MedicalHistoryController {

    private final CreateMedicalHistoryUseCase createMedicalHistoryUseCase;
    private final GetMedicalHistoryUseCase getMedicalHistoryUseCase;

    public MedicalHistoryController() {
        this.getMedicalHistoryUseCase = null;
        this.createMedicalHistoryUseCase = null;
    }

    @Inject
    public MedicalHistoryController(CreateMedicalHistoryUseCase createMedicalHistoryUseCase, GetMedicalHistoryUseCase getMedicalHistoryUseCase) {
        this.createMedicalHistoryUseCase = createMedicalHistoryUseCase;
        this.getMedicalHistoryUseCase = getMedicalHistoryUseCase;
    }

    @POST
    public Response createMedicalHistory(CreateMedicalHistoryRequest request) {
        assert createMedicalHistoryUseCase != null;
        MedicalHistory history = createMedicalHistoryUseCase.create(new PatientId(request.patientId()));
        MedicalHistoryResponse response = MedicalHistoryResourceAssembler.toResource(history);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/hello") // será accesible en /medical-histories/hello
    public Response helloWorld() {
        return Response.ok("{\"message\":\"Hello World\"}").build();
    }

    @GET
    @Path("/{id}")
    public Response getMedicalHistory(@PathParam("id") int id) {
        assert getMedicalHistoryUseCase != null;
        MedicalHistory history = getMedicalHistoryUseCase.getById(new MedicalHistoryId(id));
        MedicalHistoryResponse response = MedicalHistoryResourceAssembler.toResource(history);

        return Response.status(Response.Status.OK).entity(response).build();

    }

    @GET
    @Path("/patient/{patient-id}")
    public Response getMedicalHistoryByPatient(@PathParam("patient-id") int patientId) {
        assert getMedicalHistoryUseCase != null;
        MedicalHistory history = getMedicalHistoryUseCase.getByPatientId(new PatientId(patientId));
        MedicalHistoryResponse response = MedicalHistoryResourceAssembler.toResource(history);
        return Response.status(Response.Status.OK).entity(response).build();


    }

}
