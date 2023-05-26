package jakarta.errores;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import model.exception.ApiError;

@Provider
public class NumberFormatExceptionMapper implements ExceptionMapper<NumberFormatException> {

    public Response toResponse(NumberFormatException exception) {
        ApiError apiError = new ApiError(exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiError)
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
