package br.com.umservodemaria.napalavra.bo;

import java.util.ArrayList;
import java.util.List;

import br.com.umservodemaria.napalavra.adapter.MyAdapter;
import br.com.umservodemaria.napalavra.dao.PersistenceDao;
import br.com.umservodemaria.napalavra.vo.Capitulo;
import br.com.umservodemaria.napalavra.vo.ItemData;
import br.com.umservodemaria.napalavra.vo.Livro;
import br.com.umservodemaria.napalavra.vo.Marcacoes;
import br.com.umservodemaria.napalavra.vo.RelacLivroCap;
import br.com.umservodemaria.napalavra.vo.Versiculo;

/**
 * Created by cleiton.dantas on 27/11/2015.
 */
public class MainBo {
    private String titulo;
    private List<Marcacoes> marcacoesList;
    private RelacLivroCap relacLivroCap;
    private List<Versiculo>  versiculoList;
    private Livro livro;
    private ItemData[] array;

    public ItemData[] recuperaVersiculosSelecionados(final PersistenceDao persistenceDao ,final int idLivroSelecionado,final int idCapituloSelecionado,final ArrayList<Livro> livroArrayList){
        livro = livroArrayList.get(idLivroSelecionado-1);//Dou -1 Pois eu passo o id mais ná verdade eu estou pegando em uma lista onde inicia com 0
        titulo = livro.getAbreviacao() + " " + livro.getCapituloList().get(idCapituloSelecionado-1).getTitulo();

        marcacoesList =  persistenceDao.recuperaMarcacoes(persistenceDao.openDB(), livro.getId(), idCapituloSelecionado);
        relacLivroCap = persistenceDao.getRelacLivroCap(persistenceDao.openDB(), livro.getId(), idCapituloSelecionado);
        versiculoList =  persistenceDao.getCapitulos(persistenceDao.openDB(), relacLivroCap.getNome_tabela());

        array = new ItemData[versiculoList.size()];

        for (int i=0;i < versiculoList.size();i++){
            array[i]= new ItemData();
            for (int x=0;x < marcacoesList.size();x++){
                if(marcacoesList.get(x).getIdVersiculo()==(i+1)){
                    array[i].setFavorito(marcacoesList.get(x).getFavorito());
                    array[i].setSublinhado(marcacoesList.get(x).getSublinhado());
                    array[i].setMarcacao_color(marcacoesList.get(x).getMarcacao_color());
                }
            }
            array[i].setTexto((i + 1) + ") " + versiculoList.get(i).getTexto());
        }
        return array;
    }
    public String getTitle(){
        return titulo;
    }

    // Método que popula os dados
    public void populaLivroList(List<Livro> livroArrayList) {
        Capitulo capitulo;
        ArrayList<Capitulo> capitulos;
        for (int c = 0; c < livroArrayList.size(); c++) {
            capitulos = new ArrayList<>();
            for (int i = 0; i < livroArrayList.get(c).getQtdCapitulos(); i++) {
                capitulo = new Capitulo();
                capitulo.setId(i);
                capitulo.setTitulo("Capitulo " + (i + 1));
                capitulos.add(capitulo);
            }
            livroArrayList.get(c).setCapituloList(capitulos);
        }
    }

    /**
     * Editor de Marcacoes
     * Obs se um item é igual a null então se entende que não será atualizado
     * @param adapter
     * @param idCapitulo
     * @param idLivro
     * @param r_Color
     * @param favorito
     * @param sublinhado
     * @return
     */

    public List<Marcacoes> marcacoesEdit(MyAdapter adapter,int idLivro,int idCapitulo,Integer r_Color,Boolean favorito,Boolean sublinhado,Boolean share ){
        Marcacoes marcacoes;
        List<Marcacoes> marcacoesList = new ArrayList<>();
        for (Integer versiculo : adapter.getSelectedItems()) {
            marcacoes = new Marcacoes();
            marcacoes.setIdNumCap(idCapitulo);
            marcacoes.setIdLivro(idLivro);
            marcacoes.setIdVersiculo((versiculo + 1));
            marcacoes.setCompartihar(share);
            marcacoes.setFavorito(favorito);
            marcacoes.setSublinhado(sublinhado);
            if(r_Color==null) {
                marcacoes.setMarcacao_color(null);
            }else{
                marcacoes.setMarcacao_color(r_Color);
            }
            marcacoesList.add(marcacoes);
        }
        return marcacoesList;
    }
}
