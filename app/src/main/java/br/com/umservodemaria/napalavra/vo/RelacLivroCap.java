package br.com.umservodemaria.napalavra.vo;

/**
 * Created by cleiton.dantas on 26/10/2015.
 */
public class RelacLivroCap {
    private int id;
    private int idLivro;
    private int idNumCap;
    private String nome_tabela;

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

    public String getNome_tabela() {
        return nome_tabela;
    }

    public void setNome_tabela(String nome_tabela) {
        this.nome_tabela = nome_tabela;
    }
}
