package com.soulware.medicalhistory.infrastructure.adapters.in.web;


import com.soulware.medicalhistory.application.ports.in.GetAllClinicalFoldersUseCase;
import com.soulware.medicalhistory.application.ports.in.GetClinicalFolderUseCase;
import com.soulware.medicalhistory.application.ports.in.GetMedicalRecordByPatientAndVersionNumberUseCase;
import com.soulware.medicalhistory.domain.queries.GetClinicalFolderByPatientIdQuery;
import com.soulware.medicalhistory.domain.queries.GetClinicalFoldersQuery;
import com.soulware.medicalhistory.domain.queries.GetMedicalRecordByPatientAndVersionNumberQuery;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.ClinicalFolderAssembler;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.GetAllClinicalFolderAssembler;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.requests.GetMedicalRecordByVersionNumberRequest;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.responses.ClinicalFolderResponse;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.responses.MedicalRecordDetailResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/clinical-folders")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClinicalFolderController {

    private final GetClinicalFolderUseCase getClinicalFolderUseCase;
    private final GetMedicalRecordByPatientAndVersionNumberUseCase getMedicalRecordByPatientAndVersionNumberUseCase;
    private final GetAllClinicalFoldersUseCase getAllClinicalFoldersUseCase;

    public ClinicalFolderController( ) {
        this.getMedicalRecordByPatientAndVersionNumberUseCase = null;
        this.getClinicalFolderUseCase = null;
        this.getAllClinicalFoldersUseCase = null;
    }

    @Inject
    public ClinicalFolderController(GetClinicalFolderUseCase getClinicalFolderUseCase, GetMedicalRecordByPatientAndVersionNumberUseCase getMedicalRecordByPatientAndVersionNumberUseCase, GetAllClinicalFoldersUseCase getAllClinicalFoldersUseCase) {
        this.getClinicalFolderUseCase = getClinicalFolderUseCase;
        this.getMedicalRecordByPatientAndVersionNumberUseCase = getMedicalRecordByPatientAndVersionNumberUseCase;
        this.getAllClinicalFoldersUseCase = getAllClinicalFoldersUseCase;
    }

    @GET
    @Path("/all/{status}")
    public Response getAllClinicalFolders(@PathParam("status") String status) {
        assert getAllClinicalFoldersUseCase != null;
        var folders = getAllClinicalFoldersUseCase.getAllClinicalFolders(new GetClinicalFoldersQuery(status));
        if (folders == null || folders.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var response = folders.stream()
                .map(GetAllClinicalFolderAssembler::fromDomain)
                .toList();

        return Response.ok(response).build();
    }

    @GET
    @Path("/medical-records/{patient-id}")
    public Response getClinicalFolderByPatient(@PathParam("patient-id") int patientId) {
        assert getClinicalFolderUseCase != null;

        var folder = getClinicalFolderUseCase.getClinicalFolderByPatientId(
                new GetClinicalFolderByPatientIdQuery(patientId)
        );
        if (folder == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        ClinicalFolderResponse dto = ClinicalFolderAssembler.toResponse(
                folder
        );
        return Response.ok(dto).build();
    }


    @GET
    @Path("/medical-records/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getMedicalRecordByVersionNumber(GetMedicalRecordByVersionNumberRequest request){
        assert getMedicalRecordByPatientAndVersionNumberUseCase != null;

        var record = getMedicalRecordByPatientAndVersionNumberUseCase
                .getMedicalRecordByPatientAndVersionNumber(
                        new GetMedicalRecordByPatientAndVersionNumberQuery(request.patientId(), request.versionNumber())
                );

        if (record == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(MedicalRecordDetailResponse.from(record)).build();

    }
}
