package aulas.web.adivinhe.rest;

import aulas.web.adivinhe.jpa.controller.JogadorController;
import aulas.web.adivinhe.jpa.controller.JogoController;
import aulas.web.adivinhe.jpa.entity.Jogador;
import aulas.web.adivinhe.jpa.entity.Jogo;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
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

    private static final SimpleDateFormat ISO_DATE = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat ISO_DATE_TIME = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

    
    @Inject
    private JogadorController jogadorController;
    
    @Inject
    private JogoController jogoController;
    
    /**
     * Retorna as informações de um jogador.
     * 
     * Exemplo usando curl:
     * 
     * curl http://localhost:8080/adivinhe/api/jogador/info/1 --header "Content-Type: application/json"
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
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        JsonObject resposta = Json.createObjectBuilder()
                .add("codigo", j.getCodigo())
                .add("apelido", j.getApelido())
                .add("nome", j.getNome())
                .add("email", j.getEmail())
                .add("dataNasc", ISO_DATE.format(j.getDataNasc()))
                .build();
        
        return Response.ok().entity(resposta).build();
    }
    
    /**
     * Retorna a lista de jogos de um jogador.
     * 
     * Exemplo usando curl:
     * 
     * curl http://localhost:8080/adivinhe/api/jogador/jogos/1 --header "Content-Type: application/json"
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
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        List<Jogo> jogos = jogoController.findByJogador(id);
        
        JsonArrayBuilder jab = Json.createArrayBuilder();
        jogos.forEach(j -> {
            JsonObject jo = Json.createObjectBuilder()
                    .add("dataHora", ISO_DATE_TIME.format(j.getJogoPK().getDataHora()))
                    .add("pontuacao", j.getPontuacao())
                    .build();
            jab.add(jo);
        });
        
        JsonObject resposta = Json.createObjectBuilder()
                .add("jogador", id)
                .add("jogos", jab)
                .build();
        
        return Response.ok().entity(resposta).build();
    }
    
    
    /**
     * Cadastra um novo jogador.
     * Exemplo usando curl:
     * 
     * curl -X POST http://localhost:8080/adivinhe/api/jogador/cadastro --header "Content-Type: application/json" \
     *  -d '{ "apelido": "fdetal", "nome": "Fulado de Tal", "email": "fdetal@email.com", "dataNasc": "1995-03-17" }'
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
            return Response.status(Response.Status.CREATED).entity(j).build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
    
}
