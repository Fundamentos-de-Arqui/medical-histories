package com.soulware.medicalhistory.infrastructure.adapters.in.web;


import com.soulware.medicalhistory.application.ports.in.CreateClinicalFolderUseCase;
import com.soulware.medicalhistory.application.ports.in.GetClinicalFolderUseCase;
import com.soulware.medicalhistory.application.ports.in.GetMedicalRecordByPatientAndVersionNumberUseCase;
import com.soulware.medicalhistory.domain.model.aggregates.ClinicalFolder;
import com.soulware.medicalhistory.domain.model.valueobjects.PatientId;
import com.soulware.medicalhistory.domain.queries.GetMedicalRecordByPatientAndVersionNumberQuery;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.CreateClinicalFolderRequest;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.ClinicalFolderResourceAssembler;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.ClinicalFolderResponse;
import com.soulware.medicalhistory.infrastructure.adapters.in.web.dto.MedicalRecordDetailResponse;
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

    private final CreateClinicalFolderUseCase createClinicalFolderUseCase;
    private final GetClinicalFolderUseCase getClinicalFolderUseCase;
    private final GetMedicalRecordByPatientAndVersionNumberUseCase getMedicalRecordByPatientAndVersionNumberUseCase;

    public ClinicalFolderController( ) {
        this.getMedicalRecordByPatientAndVersionNumberUseCase = null;
        this.getClinicalFolderUseCase = null;
        this.createClinicalFolderUseCase = null;
    }

    @Inject
    public ClinicalFolderController(CreateClinicalFolderUseCase createClinicalFolderUseCase, GetClinicalFolderUseCase getClinicalFolderUseCase, GetMedicalRecordByPatientAndVersionNumberUseCase getMedicalRecordByPatientAndVersionNumberUseCase) {
        this.createClinicalFolderUseCase = createClinicalFolderUseCase;
        this.getClinicalFolderUseCase = getClinicalFolderUseCase;
        this.getMedicalRecordByPatientAndVersionNumberUseCase = getMedicalRecordByPatientAndVersionNumberUseCase;
    }

    //listener
    @POST
    public Response createMedicalHistory(CreateClinicalFolderRequest request) {
        assert createClinicalFolderUseCase != null;
        ClinicalFolder history = createClinicalFolderUseCase.create(new PatientId(request.patientId()));
        ClinicalFolderResponse response = ClinicalFolderResourceAssembler.toResource(history);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    //lista de medical records de un paciente de forma desc (created_at) y datos del archivo medico
    @GET
    @Path("/medical-records/{patient-id}")
    public Response getMedicalHistoryByPatient(@PathParam("patient-id") int patientId) {
        assert getClinicalFolderUseCase != null;
        ClinicalFolder history = getClinicalFolderUseCase.getByPatientId(new PatientId(patientId));
        ClinicalFolderResponse response = ClinicalFolderResourceAssembler.toResource(history);
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
