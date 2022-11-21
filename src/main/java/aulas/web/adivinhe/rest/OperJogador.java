package aulas.web.adivinhe.rest;

import aulas.web.adivinhe.jpa.controller.JogadorController;
import aulas.web.adivinhe.jpa.controller.JogoController;
import aulas.web.adivinhe.jpa.entity.Jogador;
import aulas.web.adivinhe.jpa.entity.Jogo;
import java.io.StringReader;
import java.util.List;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.persistence.Tuple;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Operações da API para jogadores.
 * @author Wilson Horstmeyer Bogado
 */
@Path("/jogador")
public class OperJogador {

    @Inject
    private JogadorController jogadorController;
    
    @Inject
    private JogoController jogoController;
    
    /**
     * Retorna as informações de um jogador.
     * 
     * Exemplo usando curl:
     * 
     * curl http://localhost:8080/adivinhe/api/jogador/info/1
     * 
     * @param id O código do jogador
     * @return Os dados do jogador
     */
    @GET
    @Path("/info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response infoJogador(@PathParam("id") Integer id) {
        Jogador j = jogadorController.findByCodigo(id);
        if (j == null) {
            return Response.noContent().build();
        }
        return Response.ok().entity(j).build();
    }
    
    /**
     * Retorna a lista de jogos de um jogador.
     * 
     * Exemplo usando curl:
     * 
     * curl http://localhost:8080/adivinhe/api/jogador/jogos/1
     * 
     * @param id O código do jogador
     * @return A lista de jogos
     */
    @GET
    @Path("/jogos/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response jogosJogador(@PathParam("id") Integer id) {
        Jogador jogador = jogadorController.findByCodigo(id);
        if (jogador == null) {
            return Response.noContent().build();
        }
        
        List<Jogo> jogos = jogoController.findByJogador(id);
        Jsonb jb = JsonbBuilder.create();        
        JsonArrayBuilder jab = Json.createArrayBuilder();
        jogos.forEach(j -> {
            JsonReader jr = Json.createReader(new StringReader(jb.toJson(j)));
//            JsonObject jo = Json.createObjectBuilder()
//                    .add("dataHora", ISO_DATE_TIME.format(j.getJogoPK().getDataHora()))
//                    .add("pontuacao", j.getPontuacao())
//                    .build();
            jab.add(jr.readObject());
        });
        
//        JsonObject resposta = Json.createObjectBuilder()
//                .add("jogador", id)
//                .add("jogos", jab)
//                .build();
        
        return Response.ok().entity(jab.build()).build();
    }
    
    
    /**
     * Cadastra um novo jogador.
     * Exemplo usando curl:
     * 
     * curl -X POST http://localhost:8080/adivinhe/api/jogador/cadastro --header "Content-Type: application/json" \
     *  -d '{ "apelido": "fdetal", "nome": "Fulado de Tal", "email": "fdetal@email.com", "dataNasc": "1995-03-17T00:00:00-03:00" }'
     * 
     * @param jogador
     * @return Resultado da operação
     */
    @POST
    @Path("/cadastro/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastroJogador(Jogador jogador) {
        try {
            Jogador j = jogadorController.merge(jogador);
            return Response.ok().entity(j).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Json.createObjectBuilder().add("erro", e.getMessage()).build())
                    .build();
        }
    }
 
    /**
     * Retorna as informações de todos os jogadores e o respectivo número
     * de jogos de cada um.
     * @return Resultado da operação
     */
    @GET
    @Path("/numjogos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response numJogos() {
        List<Tuple> tuplas = jogoController.numJogos();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        Jsonb jb = JsonbBuilder.create();
        tuplas.forEach(t -> {
            Jogador j = t.get("jogador", Jogador.class);
            JsonReader jr = Json.createReader(new StringReader(jb.toJson(j)));
            JsonObjectBuilder job = Json.createObjectBuilder()
               .add("jogador", jr.readObject())
               .add("numJogos", t.get("numJogos", Long.class));
            jab.add(job);
        });
        return Response.ok().entity(jab.build()).build();
    }
}
