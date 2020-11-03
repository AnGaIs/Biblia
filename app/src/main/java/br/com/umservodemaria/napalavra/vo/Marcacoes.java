package br.com.umservodemaria.napalavra.vo;

/**
 * Created by cleiton.dantas on 11/11/2015.
 */
public class Marcacoes {
    private int id;
    private int idLivro;
    private int idNumCap;
    private int idVersiculo;
    private Boolean sublinhado;
    private Integer marcacao_color;
    private Boolean favorito;
    private Boolean compartihar;
    private Integer idAnotacoes;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    public int getIdNumCap() {
        return idNumCap;
    }

    public void setIdNumCap(int idNumCap) {
        this.idNumCap = idNumCap;
    }

    public int getIdVersiculo() {
        return idVersiculo;
    }

    public void setIdVersiculo(int idVersiculo) {
        this.idVersiculo = idVersiculo;
    }

    public Boolean getSublinhado() {
        return sublinhado;
    }

    public void setSublinhado(Boolean sublinhado) {
        this.sublinhado = sublinhado;
    }

    public Integer getMarcacao_color() {
        return marcacao_color;
    }

    public void setMarcacao_color(Integer marcacao_color) {
        this.marcacao_color = marcacao_color;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public Boolean getCompartihar() {
        return compartihar;
    }

    public void setCompartihar(Boolean compartihar) {
        this.compartihar = compartihar;
    }

    public Integer getIdAnotacoes() {
        return idAnotacoes;
    }

    public void setIdAnotacoes(Integer idAnotacoes) {
        this.idAnotacoes = idAnotacoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Marcacoes)) return false;

        Marcacoes marcacoes = (Marcacoes) o;

        return getIdVersiculo() == marcacoes.getIdVersiculo();

    }

    @Override
    public int hashCode() {
        return getIdVersiculo();
    }
}
