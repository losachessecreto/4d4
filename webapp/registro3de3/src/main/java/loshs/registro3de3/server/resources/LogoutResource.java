package loshs.registro3de3.server.resources;

import java.net.URI;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import loshs.registro3de3.server.beans.DatasourceContainer;

@ManagedBean
@Path("logout")
public class LogoutResource {

    @Context
    DatasourceContainer dsc;

    @Context
    HttpServletRequest request;

    static final Logger LOGGER = Logger.getLogger(LogoutResource.class.getName());

    @GET
    public Response logout() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            //session.setMaxInactiveInterval(1);

        }
        URI uri = URI.create(request.getContextPath() + "/login");
        return Response.seeOther(uri).build();
        //return Response.status(Status.UNAUTHORIZED).entity(new HTTPJsonResponseObject(401, "Unauthorized",
          //      "El usuario o constraseña son incorrectos", Boolean.FALSE)).build();
    }

  
}
