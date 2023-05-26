package jakarta.rest.logeo;

import domain.service.ServiceLogeo;
import domain.service.impl.ServiceLogeoImpl;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import model.Usuario;
import utils.PathsConstants;

import java.util.concurrent.atomic.AtomicReference;


@Path(PathsConstants.PATH_REGISTER)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log4j2
public class RestRegister {

    final ServiceLogeo serviceLog;
    final Pbkdf2PasswordHash passwordHash;

    @Context
    HttpServletRequest req;

    @Inject
    public RestRegister(ServiceLogeoImpl serviceLog, Pbkdf2PasswordHash passwordHash) {
        this.serviceLog = serviceLog;
        this.passwordHash = passwordHash;
    }

    @POST
    public Response getRegister(Usuario userRegisterDTO) throws MessagingException {
        AtomicReference<Response> response = new AtomicReference<>();

        if (userRegisterDTO.getNombre() != null && userRegisterDTO.getContrasenia() != null) {
            String hash = passwordHash.generate(userRegisterDTO.getContrasenia().toCharArray());
            Usuario user = serviceLog.getByUsername(userRegisterDTO.getNombre());
            if (user == null) {
                try {
                    Usuario myUser = Usuario.builder()
                            .nombre(userRegisterDTO.getNombre())
                            .contrasenia(hash)
                            .tipo(userRegisterDTO.getTipo())
                            .habilidad(userRegisterDTO.getHabilidad())
                            .build();

                    serviceLog.add(myUser);
                    response.set(Response.ok().entity(true).build());
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            } else {
                response.set(Response.status(Response.Status.NOT_ACCEPTABLE).entity("User just exist").build());
            }
        }

        return response.get();
    }
}
