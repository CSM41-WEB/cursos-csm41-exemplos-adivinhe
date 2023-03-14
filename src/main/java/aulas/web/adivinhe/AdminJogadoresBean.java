package aulas.web.adivinhe;

import aulas.web.adivinhe.jpa.controller.JogadorController;
import aulas.web.adivinhe.jpa.entity.Jogador;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Suporte à view de administração de jogadores.
 * @author Wilson Horstmeyer Bogado
 */
@Named
@ViewScoped
public class AdminJogadoresBean implements Serializable {

    private List<Jogador> jogadores;

    @Inject
    private JogadorController jogadorController;

    public AdminJogadoresBean() {
    }

    public List<Jogador> getJogadores() {
        if (jogadores == null) {
            jogadores = jogadorController.findAll();
        }
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}
