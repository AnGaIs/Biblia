package br.com.umservodemaria.napalavra.vo;

/**
 * Created by cleiton.dantas on 01/12/2015.
 */
public class ItemFavorito {
    private int idLivro;
    private int idCapitulo;
    private int idVersiculo;
    private int posicaoScroll;
    private String titulo;
    private String texto;


    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    public int getIdCapitulo() {
        return idCapitulo;
    }

    public void setIdCapitulo(int idCapitulo) {
        this.idCapitulo = idCapitulo;
    }

    public int getIdVersiculo() {
        return idVersiculo;
    }

    public void setIdVersiculo(int idVersiculo) {
        this.idVersiculo = idVersiculo;
    }

    public int getPosicaoScroll() {
        return posicaoScroll;
    }

    public void setPosicaoScroll(int posicaoScroll) {
        this.posicaoScroll = posicaoScroll;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
