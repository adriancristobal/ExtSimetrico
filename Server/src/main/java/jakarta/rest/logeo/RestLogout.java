package jakarta.rest.logeo;

import domain.service.ServiceLogeo;
import domain.service.impl.ServiceLogeoImpl;
import jakarta.common.Constants;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.log4j.Log4j2;
import utils.PathsConstants;

@Path(PathsConstants.PATH_LOGOUT)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestLogout {

    ServiceLogeo serviceLog;
    @Context
    HttpServletRequest req;

    @Inject
    public RestLogout(ServiceLogeoImpl serviceLog) {
        this.serviceLog = serviceLog;
    }


    @GET
    public Boolean getLogout() {
        req.getSession().removeAttribute(Constants.LOGIN);
        return true;
    }
}
