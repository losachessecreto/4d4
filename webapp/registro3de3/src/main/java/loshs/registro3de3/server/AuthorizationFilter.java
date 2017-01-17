package loshs.registro3de3.server;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import loshs.registro3de3.server.beans.HTTPJsonResponseObject;

@Provider
public class AuthorizationFilter implements ContainerRequestFilter {

    @Context
    HttpServletRequest request;

    static final Logger LOGGER = Logger.getLogger(AuthorizationFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String path = requestContext.getUriInfo().getPath();
        if (path != null && !path.contains("login") && !path.contains("test")) {
            HttpSession session = request.getSession(false);
            if (session == null) {
                requestContext.abortWith(Response
                        .status(Response.Status.UNAUTHORIZED)
                        .entity(new HTTPJsonResponseObject(401, "Unauthorized", "No autorizado"))
                        .build());
            }
        }
    }
}
