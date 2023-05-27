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
@RolesAllowed({Constants.ROLE_CONTRATISTA})
public class RestContrato {

    private final ServiceContrato serviceContrato;
    private final ServiceSicarioContrato service;

    @Inject
    public RestContrato(ServiceContratoImpl serviceContrato, ServiceSicarioContrato service) {
        this.serviceContrato = serviceContrato;
        this.service = service;
    }

    @GET
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
    public Response getSicariosByHabilityLevel(@QueryParam(Constants.HABILITY_LEVEL) Integer habilityLevel) {
        AtomicReference<Response> r = new AtomicReference<>();
        service.getSicariosByHabilityLevel(habilityLevel)
                .peek(list -> r.set(Response.ok(list).build()))
                .peekLeft(apiError -> r.set(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(apiError)
                        .build()));
        return r.get();
    }
    @POST
    public Response add(Contrato contrato) {
        serviceContrato.add(contrato);
        //devolvemos un response en vez de solo el objeto ingredient para que el status sea 201
        return Response.status(Response.Status.CREATED).entity(contrato).build();

    }

    @DELETE
    public Response delete(@QueryParam(QueryParamsConstants.ID) String id) {
        if (serviceContrato.delete(Integer.parseInt(id))) {
            return Response.status(Response.Status.NO_CONTENT).entity(id).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @PUT
    public Contrato update(Contrato contrato) {
        return serviceContrato.update(contrato);
    }

}
