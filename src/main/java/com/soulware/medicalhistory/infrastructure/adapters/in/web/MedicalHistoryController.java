package com.soulware.medicalhistory.infrastructure.adapters.in.web;


import com.soulware.medicalhistory.application.ports.in.CreateMedicalHistoryUseCase;
import com.soulware.medicalhistory.application.ports.in.GetMedicalHistoryUseCase;
import com.soulware.medicalhistory.application.ports.in.GetMedicalRecordByPatientAndVersionNumberUseCase;
import com.soulware.medicalhistory.domain.model.aggregates.MedicalHistory;
import com.soulware.medicalhistory.domain.model.aggregates.MedicalRecord;
import com.soulware.medicalhistory.domain.model.valueobjects.MedicalHistoryId;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;
import com.soulware.medicalhistory.domain.queries.GetMedicalRecordByPatientAndVersionNumberQuery;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.CreateMedicalHistoryRequest;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.MedicalHistoryResourceAssembler;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.MedicalHistoryResponse;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.MedicalRecordDetailResponse;
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
    private final GetMedicalRecordByPatientAndVersionNumberUseCase getMedicalRecordByPatientAndVersionNumberUseCase;

    public MedicalHistoryController( ) {
        this.getMedicalRecordByPatientAndVersionNumberUseCase = null;
        this.getMedicalHistoryUseCase = null;
        this.createMedicalHistoryUseCase = null;
    }

    @Inject
    public MedicalHistoryController(CreateMedicalHistoryUseCase createMedicalHistoryUseCase, GetMedicalHistoryUseCase getMedicalHistoryUseCase, GetMedicalRecordByPatientAndVersionNumberUseCase getMedicalRecordByPatientAndVersionNumberUseCase) {
        this.createMedicalHistoryUseCase = createMedicalHistoryUseCase;
        this.getMedicalHistoryUseCase = getMedicalHistoryUseCase;
        this.getMedicalRecordByPatientAndVersionNumberUseCase = getMedicalRecordByPatientAndVersionNumberUseCase;
    }

    //listener
    @POST
    public Response createMedicalHistory(CreateMedicalHistoryRequest request) {
        assert createMedicalHistoryUseCase != null;
        MedicalHistory history = createMedicalHistoryUseCase.create(new PatientId(request.patientId()));
        MedicalHistoryResponse response = MedicalHistoryResourceAssembler.toResource(history);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    //lista de medical records de un paciente de forma desc (created_at) y datos del archivo medico
    @GET
    @Path("/medical-records/{patient-id}")
    public Response getMedicalHistoryByPatient(@PathParam("patient-id") int patientId) {
        assert getMedicalHistoryUseCase != null;
        MedicalHistory history = getMedicalHistoryUseCase.getByPatientId(new PatientId(patientId));
        MedicalHistoryResponse response = MedicalHistoryResourceAssembler.toResource(history);
        return Response.status(Response.Status.OK).entity(response).build();
    }


    //medical record de un paciente por version
    @GET
    @Path("/medical-records/{patient-id}/{version-number}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedicalRecordByVersionNumber(@PathParam("patient-id") int patientId, @PathParam("version-number") int versionNumber){
        assert getMedicalRecordByPatientAndVersionNumberUseCase != null;

        var record = getMedicalRecordByPatientAndVersionNumberUseCase
                .getMedicalRecordByPatientAndVersionNumber(
                        new GetMedicalRecordByPatientAndVersionNumberQuery(patientId, versionNumber)
                );

        if (record == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(MedicalRecordDetailResponse.from(record)).build();


    }

}
