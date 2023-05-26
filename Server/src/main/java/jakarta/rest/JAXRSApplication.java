package jakarta.rest;


import jakarta.annotation.security.DeclareRoles;
import jakarta.common.Constants;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import utils.PathsConstants;

@ApplicationPath(PathsConstants.APPLICATION_PATH)
@DeclareRoles({Constants.ROLE_SICARIO, Constants.ROLE_CONTRATISTA})
public class JAXRSApplication extends Application {

}
