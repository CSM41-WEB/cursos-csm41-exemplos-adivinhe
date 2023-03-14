package aulas.web.adivinhe.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

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
