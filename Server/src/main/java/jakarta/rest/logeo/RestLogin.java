package jakarta.rest.logeo;

import domain.service.ServiceLogeo;
import domain.service.impl.ServiceLogeoImpl;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import model.Usuario;
import utils.PathsConstants;
import utils.QueryParamsConstants;


@Path(PathsConstants.PATH_LOGIN)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestLogin {

    final ServiceLogeo serviceLogeo;

    @Context
    HttpServletRequest req;

    @Context
    HttpServletResponse res;

    @Inject
    public RestLogin(ServiceLogeoImpl serviceLogeo) {
        this.serviceLogeo = serviceLogeo;
    }


    @GET
    public Response getLogin(@QueryParam(QueryParamsConstants.USERNAME) String username, @QueryParam(QueryParamsConstants.PASSWORD) String password) {

        if (username != null && password != null) {
            Usuario user = serviceLogeo.getByUsername(username);
            serviceLogeo.login(user, password.toCharArray(), user.getContrasenia());
            String jwt = serviceLogeo.generateJWS(user);
            res.setHeader(HttpHeaders.AUTHORIZATION, jwt);
            return Response.status(Response.Status.ACCEPTED).header(HttpHeaders.AUTHORIZATION, jwt).entity(user).build();

        }

        return Response.ok().build();
    }

}
