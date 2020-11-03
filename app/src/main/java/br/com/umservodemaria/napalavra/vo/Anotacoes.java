package br.com.umservodemaria.napalavra.vo;


import java.util.List;

/**
 * Created by cleiton.dantas on 02/05/2016.
 */

public class Anotacoes {


    public  Anotacoes(){
    }


    public  Anotacoes(Long id){
        this.id = id;
    }

    private Long id;
    private int idLivro;
    private int idNumCap;
    private List<Versiculo> versiculoList;
    private String versiculos;
    private String comentarios;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getVersiculos() {
        return versiculos;
    }

    public void setVersiculos(String versiculos) {
        this.versiculos = versiculos;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public int getIdNumCap() {
        return idNumCap;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    public void setIdNumCap(int idNumCap) {
        this.idNumCap = idNumCap;
    }

    public void setVersiculoList(List<Versiculo> versiculoList) {
        this.versiculoList = versiculoList;
    }

    public List<Versiculo> getVersiculoList() {
        return versiculoList;
    }
}
