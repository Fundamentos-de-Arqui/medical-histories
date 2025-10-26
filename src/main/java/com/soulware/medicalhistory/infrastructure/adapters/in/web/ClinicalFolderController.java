package com.soulware.medicalhistory.infrastructure.adapters.in.web;


import com.soulware.medicalhistory.application.ports.in.CreateClinicalFolderUseCase;
import com.soulware.medicalhistory.application.ports.in.GetClinicalFolderUseCase;
import com.soulware.medicalhistory.application.ports.in.GetMedicalRecordByPatientAndVersionNumberUseCase;
import com.soulware.medicalhistory.domain.queries.GetMedicalFolderByPatientIdQuery;
import com.soulware.medicalhistory.domain.queries.GetMedicalRecordByPatientAndVersionNumberQuery;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.ClinicalFolderAssembler;
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

    public ClinicalFolderController( ) {
        this.getMedicalRecordByPatientAndVersionNumberUseCase = null;
        this.getClinicalFolderUseCase = null;
    }

    @Inject
    public ClinicalFolderController(GetClinicalFolderUseCase getClinicalFolderUseCase, GetMedicalRecordByPatientAndVersionNumberUseCase getMedicalRecordByPatientAndVersionNumberUseCase) {
        this.getClinicalFolderUseCase = getClinicalFolderUseCase;
        this.getMedicalRecordByPatientAndVersionNumberUseCase = getMedicalRecordByPatientAndVersionNumberUseCase;
    }

    //lista de medical records de un paciente de forma desc (created_at) y datos del archivo medico 🆗
    @GET
    @Path("/medical-records/{patient-id}")
    public Response getClinicalFolderByPatient(@PathParam("patient-id") int patientId) {
        assert getClinicalFolderUseCase != null;

        var folder = getClinicalFolderUseCase.getClinicalFolderByPatientId(
                new GetMedicalFolderByPatientIdQuery(patientId)
        );
        if (folder == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        ClinicalFolderResponse dto = ClinicalFolderAssembler.toResponse(
                folder
        );
        return Response.ok(dto).build();
    }


    //medical record de un paciente por version 🆗
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
        //SIGNATURE
        //WALTER FIRMA A TU HIJO
        //Bajada de Palanca

    }

}
