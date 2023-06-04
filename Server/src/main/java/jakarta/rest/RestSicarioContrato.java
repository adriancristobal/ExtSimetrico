package jakarta.rest;

import domain.service.ServiceSicarioContrato;
import domain.service.impl.ServiceSicarioContratoImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.common.Constants;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.SicarioContrato;
import utils.PathsConstants;

import java.util.concurrent.atomic.AtomicReference;

@Path(PathsConstants.PATH_SICARIO_CONTRATO)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestSicarioContrato {

    private final ServiceSicarioContrato service;

    @Inject
    public RestSicarioContrato(ServiceSicarioContratoImpl service) {
        this.service = service;
    }


    @GET
    @RolesAllowed({Constants.ROLE_SICARIO})
    public Response getAll() {
        AtomicReference<Response> r = new AtomicReference<>();
        service.getAll()
                .peek(list -> r.set(Response.ok(list).build()))
                .peekLeft(apiError -> r.set(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(apiError)
                        .build()));
        return r.get();
    }

    @GET
    @Path(PathsConstants.PATH_ID_SICARIO)
    @RolesAllowed({Constants.ROLE_SICARIO})
    public Response getAllBySicario(@QueryParam(Constants.IDD_SICARIO) Integer idSicario) {
        AtomicReference<Response> r = new AtomicReference<>();
        service.getSicariosContratosBySicario(idSicario)
                .peek(list -> r.set(Response.ok(list).build()))
                .peekLeft(apiError -> r.set(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(apiError)
                        .build()));
        return r.get();
    }



    @POST
    public Response add(SicarioContrato sicarioContrato) {
        service.add(sicarioContrato);
        //devolvemos un response en vez de solo el objeto ingredient para que el status sea 201
        return Response.status(Response.Status.CREATED).entity(sicarioContrato).build();
    }

    @DELETE
    @RolesAllowed({Constants.ROLE_SICARIO})
    public Response delete(@QueryParam(Constants.ID_CONTRATO) String idContrato, @QueryParam(Constants.ID_SICARIO) String idSicario) {
        if (service.delete(Integer.parseInt(idContrato), Integer.parseInt(idSicario))) {
            return Response.status(Response.Status.NO_CONTENT).entity(idContrato + idSicario).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @RolesAllowed({Constants.ROLE_SICARIO})
    public SicarioContrato update(SicarioContrato sicarioContrato) {
        return service.update(sicarioContrato);
    }



}
