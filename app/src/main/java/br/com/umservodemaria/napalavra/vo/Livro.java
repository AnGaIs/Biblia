package br.com.umservodemaria.napalavra.vo;

import java.util.List;

/**
 * Created by cleiton.dantas on 23/10/2015.
 */
public class Livro {
    private int id;
    private String tituloLivro;
    private String abreviacao;
    private int qtdCapitulos;
    private List<Capitulo> capituloList;

    public Livro() {
    }
    public Livro(int id) {
        this.id = id;
    }

    public int getQtdCapitulos() {
        return qtdCapitulos;
    }

    public void setQtdCapitulos(int qtdCapitulos) {
        this.qtdCapitulos = qtdCapitulos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTituloLivro() {
        return tituloLivro;
    }
    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }
    public List<Capitulo> getCapituloList() {
        return capituloList;
    }

    public void setCapituloList(List<Capitulo> capituloList) {
        this.capituloList = capituloList;
    }

    public String getAbreviacao() {
        return abreviacao;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Livro livro = (Livro) o;

        return getId() == livro.getId();

    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        return tituloLivro.toString();
    }
}
