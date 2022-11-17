package aulas.web.adivinhe.rest;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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

    @GET
    @Path("/ola/{apelido}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ola(@PathParam("apelido") String apelido) {
        JsonObject resposta = Json.createObjectBuilder()
                .add("mensagem", "Olá, " + apelido)
                .build();
        return Response.ok().entity(resposta).build();
    }

}
