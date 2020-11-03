package br.com.umservodemaria.napalavra.vo;

import java.util.List;

/**
 * Created by cleiton.dantas on 23/10/2015.
 */
public class Capitulo {
    private int id;
    private String titulo;
    private List<Versiculo> versiculoList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Versiculo> getVersiculoList() {
        return versiculoList;
    }

    public void setVersiculoList(List<Versiculo> versiculoList) {
        this.versiculoList = versiculoList;
    }

    @Override
    public String toString() {
        return titulo.toString();
    }
}
