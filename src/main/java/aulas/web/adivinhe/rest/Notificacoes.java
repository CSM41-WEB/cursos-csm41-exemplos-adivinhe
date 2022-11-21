package aulas.web.adivinhe.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Envia notificações.
 * @author Wilson Horstmeyer Bogado
 */
@Path("/notificacoes")
public class Notificacoes {
    @GET
    @Path("/ping")
    public Response ping() {
        return Response.ok().entity("Serviço normal").build();
    }
}
