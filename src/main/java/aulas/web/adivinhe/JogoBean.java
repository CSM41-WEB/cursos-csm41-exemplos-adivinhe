package aulas.web.adivinhe;

import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 * Suporte à página de adivinhação de número.
 * @author Wilson Horstmeyer Bogado
 */
@Named
@ViewScoped
public class JogoBean implements Serializable {
    private Integer palpite;
    private Integer palpiteAnterior;
    private boolean certo = false;

    @Inject
    private JogadorBean jogadorBean;

    public Integer getPalpite() {
        return palpite;
    }

    public void setPalpite(Integer palpite) {
        this.palpite = palpite;
    }

    public Integer getPalpiteAnterior() {
        return palpiteAnterior;
    }

    public boolean isCerto() {
        return this.certo;
    }

    public void tentar() {
        jogadorBean.incrementarTentativas();
        certo = palpite == jogadorBean.getNumeroAtual();
        palpiteAnterior = palpite;
        palpite = null;
    }

    public String reiniciar() {
        jogadorBean.jogarNovamente();
        palpite = palpiteAnterior = null;
        certo = false;
        return "index?faces-redirect=true";
    }
    
    public void iniciarJogo(ActionEvent event) {
        jogadorBean.jogarNovamente();
        palpite = palpiteAnterior = null;
        certo = false;
    }
}
