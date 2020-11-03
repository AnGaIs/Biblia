package br.com.umservodemaria.napalavra.vo;

/**
 * Created by cleiton.dantas on 23/10/2015.
 */
public class Versiculo {
        private Long id;
        private String texto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto.toString();
    }
}
