package jakarta.rest;

import domain.service.ServiceContrato;
import domain.service.ServiceSicarioContrato;
import domain.service.impl.ServiceContratoImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.common.Constants;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Contrato;
import utils.PathsConstants;
import utils.QueryParamsConstants;

import java.util.concurrent.atomic.AtomicReference;

@Path(PathsConstants.PATH_CONTRATO)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestContrato {

    private final ServiceContrato serviceContrato;
    private final ServiceSicarioContrato service;

    @Inject
    public RestContrato(ServiceContratoImpl serviceContrato, ServiceSicarioContrato service) {
        this.serviceContrato = serviceContrato;
        this.service = service;
    }

    @GET
    @RolesAllowed({Constants.ROLE_CONTRATISTA})
    public Response getAll() {
        AtomicReference<Response> r = new AtomicReference<>();
        serviceContrato.getAll()
                .peek(list -> r.set(Response.ok(list).build()))
                .peekLeft(apiError -> r.set(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(apiError)
                        .build()));
        return r.get();
    }

    @GET
    @Path(PathsConstants.PATH_BY_HABILITY_LEVEL)
    @RolesAllowed({Constants.ROLE_CONTRATISTA})
    public Response getSicariosByHabilityLevel(@QueryParam(Constants.HABILITY_LEVEL) Integer habilityLevel) {
        AtomicReference<Response> r = new AtomicReference<>();
        service.getSicariosByHabilityLevel(habilityLevel)
                .peek(list -> r.set(Response.ok(list).build()))
                .peekLeft(apiError -> r.set(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(apiError)
                        .build()));
        return r.get();
    }

    @GET
    @Path(PathsConstants.PATH_ID_CONTRATISTA)
    @RolesAllowed({Constants.ROLE_CONTRATISTA})
    public Response getContratosByIdContratista(@QueryParam(Constants.IDD_CONTRATISTA) Integer idContratista) {
        AtomicReference<Response> r = new AtomicReference<>();
        serviceContrato.getContratosByIdContratista(idContratista)
                .peek(list -> r.set(Response.ok(list).build()))
                .peekLeft(apiError -> r.set(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(apiError)
                        .build()));
        return r.get();
    }

    @GET
    @Path(PathsConstants.PATH_ID_SICARIO)
    @RolesAllowed({Constants.ROLE_SICARIO})
    public Response getContratosByIdSicario(@QueryParam(Constants.IDD_SICARIO) Integer idSicario) {
        AtomicReference<Response> r = new AtomicReference<>();
        serviceContrato.getContratosByIdSicario(idSicario)
                .peek(list -> r.set(Response.ok(list).build()))
                .peekLeft(apiError -> r.set(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(apiError)
                        .build()));
        return r.get();
    }

    @POST
    @RolesAllowed({Constants.ROLE_CONTRATISTA})
    public Response add(Contrato contrato) {
        serviceContrato.add(contrato);
        //devolvemos un response en vez de solo el objeto ingredient para que el status sea 201
        return Response.status(Response.Status.CREATED).entity(contrato).build();

    }

    @DELETE
    @RolesAllowed({Constants.ROLE_CONTRATISTA})
    public Response delete(@QueryParam(QueryParamsConstants.ID) String id) {
        if (serviceContrato.delete(Integer.parseInt(id))) {
            return Response.status(Response.Status.NO_CONTENT).entity(id).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @PUT
    @RolesAllowed({Constants.ROLE_CONTRATISTA})
    public Contrato update(Contrato contrato) {
        return serviceContrato.update(contrato);
    }

}
