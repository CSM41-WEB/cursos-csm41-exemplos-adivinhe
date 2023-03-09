package aulas.web.adivinhe.jpa.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Representa um registro de jogo no banco de dados.
 * @author Wilson Horstmeyer Bogado
 *
 * Script de criação da tabels:
 *       create table jogo (
 *           cod_jogador int not null,
 *           data_hora timestamp not null,
 *           pontuacao int not null,
 *           primary key(cod_jogador, data_hora),
 *           foreign key(cod_jogador) references jogador(codigo)
 *       );
 */
@Entity
@Table(name = "jogo")
public class Jogo implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private JogoPK jogoPK;

    @NotNull
    private Integer pontuacao;

    @ManyToOne
    @JoinColumn(name = "cod_jogador", referencedColumnName = "codigo", insertable = false, updatable = false)
    private Jogador jogador;

    public JogoPK getJogoPK() {
        return jogoPK;
    }

    public void setJogoPK(JogoPK jogoPK) {
        this.jogoPK = jogoPK;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.jogoPK);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Jogo other = (Jogo) obj;
        if (!Objects.equals(this.jogoPK, other.jogoPK)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Jogo{" + "JogoPK=" + jogoPK + '}';
    }
}
