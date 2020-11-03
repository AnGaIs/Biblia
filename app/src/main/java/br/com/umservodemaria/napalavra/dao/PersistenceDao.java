package br.com.umservodemaria.napalavra.dao;

/**
 * Created by cleiton.dantas on 23/10/2015.
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.umservodemaria.napalavra.vo.Anotacoes;
import br.com.umservodemaria.napalavra.vo.ItemFavorito;
import br.com.umservodemaria.napalavra.vo.Livro;
import br.com.umservodemaria.napalavra.vo.Marcacoes;
import br.com.umservodemaria.napalavra.vo.RelacLivroCap;
import br.com.umservodemaria.napalavra.vo.Versiculo;


public class PersistenceDao extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "DB_MINHABIBLIACATOLICAV8";
    private static final String COLUMN_ID = "ID";

    private static final String TABLE_LIVROS = "LIVROS";
    private static final String COLUMN_TITULO_LIVRO = "TITULOLIVRO";
    private static final String COLUMN_ABREVIACAO = "ABREVIACAO";
    private static final String COLUMN_QTDCAPITULOS = "QTDCAPITULOS";
    private static final String TABLE_RELAC_LIVRO_CAP = "TB_RELAC_LIVRO_CAP";

    private static final String TB_RELAC_ANOTACOES_VERSICULOS = "TB_RELAC_ANOTACOES_VERSICULOS";
    private static final String COLUMN_ID_ANOTACOES = "ID_ANOTACOES";
    private static final String COLUMN_ID_VERSICULO = "ID_VERSICULO";

    private static final String COLUMN_IDLIVRO = "IDLIVRO";
    private static final String COLUMN_IDNUMCAP = "IDNUMCAP";
    private static final String COLUMN_NOME_TABELA = "NOME_TABELA";
    private static final String COLUMN_VERSICULO = "VER";
    private static final String TABLE_MARCACOES = "MARCACOES";
    private static final String COLUMN_SUBLINHADO ="SUBLINHADO";
    private static final String COLUMN_MARCACAO_COLOR ="MARCACAO_COLOR";
    private static final String COLUMN_FAVORITO ="FAVORITO";
    private static final String COLUMN_IDANOTACOES = "IDANOTACOES";

    private static final String TABLE_ANOTACOES = "TB_ANOTACOES";
    private static final String COLUMN_VERSICULOS = "VERSICULOS";
    private static final String COLUMN_COMENTARIOS = "COMENTARIOS";

    private Cursor cursor;
    private static PersistenceDao instance;
    private static final int VERSAO =1;
    public static SQLiteDatabase bancoDados = null;
    private static Context contextStatic;

    public PersistenceDao(Context context) {
        super(context, DATABASE_NAME, null, VERSAO);
        contextStatic =context;
    }
    public static PersistenceDao getInstance(Context context) {
        if(instance == null)
            instance = new PersistenceDao(context);
        return instance;
    }

    /**
     * Recupera o relacionamento dos livros pelo id do livro e o numero do capitulo
     */
    public RelacLivroCap getRelacLivroCap(SQLiteDatabase bancoDados,int idLivro,int idNumCap){
        String query =  COLUMN_IDLIVRO+" = ? AND "+COLUMN_IDNUMCAP +" = ?";
        String[] args = {""+idLivro,""+idNumCap};
        cursor = bancoDados.query(TABLE_RELAC_LIVRO_CAP, new String[]{COLUMN_ID,COLUMN_IDLIVRO,COLUMN_IDNUMCAP,COLUMN_NOME_TABELA},query,args,null,null,null);
        RelacLivroCap relacLivroCap =null;
        while(cursor.moveToNext()){
            relacLivroCap = new RelacLivroCap();
            relacLivroCap.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
            relacLivroCap.setIdLivro(idLivro);
            relacLivroCap.setIdNumCap(idNumCap);
            relacLivroCap.setNome_tabela(cursor.getString(cursor.getColumnIndex(COLUMN_NOME_TABELA)));
            break;
        }
        bancoDados.close();
        return relacLivroCap;
    }

    /**
     *
     */
    public List<Anotacoes>  getAnotacoes(SQLiteDatabase bancoDados,int id){
        String query =  COLUMN_ID+" = ? ";
        String[] args = {""+id};
        cursor = bancoDados.query(TABLE_ANOTACOES, new String[]{COLUMN_ID,COLUMN_VERSICULOS,COLUMN_COMENTARIOS},query,args,null,null,null);
        Anotacoes anotacoes =null;
        List<Anotacoes> anotacoesList = new ArrayList<Anotacoes>();
        while(cursor.moveToNext()){
            anotacoes = new Anotacoes();
            anotacoes.setId(Long.parseLong(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
            anotacoes.setComentarios(cursor.getString(cursor.getColumnIndex(COLUMN_COMENTARIOS)));
            anotacoes.setVersiculos(cursor.getString(cursor.getColumnIndex(COLUMN_VERSICULOS)));
            anotacoesList.add(anotacoes);
        }
        bancoDados.close();
        return anotacoesList;
    }


    public void salvarAnotacoes(SQLiteDatabase bancoDados, Anotacoes anotacoes){
        ContentValues valuesAntacoes = new ContentValues();
        valuesAntacoes.put(COLUMN_VERSICULOS,anotacoes.getVersiculos());
        valuesAntacoes.put(COLUMN_COMENTARIOS,anotacoes.getComentarios());
        valuesAntacoes.put(COLUMN_COMENTARIOS,anotacoes.getComentarios());
        bancoDados.insert(TABLE_ANOTACOES, null, valuesAntacoes);

        for(Versiculo item: anotacoes.getVersiculoList()){
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID_ANOTACOES,anotacoes.getId());
            values.put(COLUMN_ID_VERSICULO,item.getId());
            bancoDados.insert(TB_RELAC_ANOTACOES_VERSICULOS, null, values);
        }
    }



    /**
     *
     */
    public List<Anotacoes>  getTodasAnotacoes(SQLiteDatabase bancoDados){
        cursor = bancoDados.query(TABLE_ANOTACOES, new String[]{COLUMN_ID,COLUMN_VERSICULOS,COLUMN_COMENTARIOS},null,null,null,null,null);
        Anotacoes anotacoes =null;
        List<Anotacoes> anotacoesList = new ArrayList<Anotacoes>();
        while(cursor.moveToNext()){
            anotacoes = new Anotacoes();
            anotacoes.setId(Long.parseLong(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
            anotacoes.setComentarios(cursor.getString(cursor.getColumnIndex(COLUMN_COMENTARIOS)));
            anotacoes.setVersiculos(cursor.getString(cursor.getColumnIndex(COLUMN_VERSICULOS)));
            anotacoesList.add(anotacoes);
        }
        bancoDados.close();
        return anotacoesList;
    }


    /**
     * Metodo Busca os titulos e Lista para ler na view
     */
    public List<Livro> getLivros(SQLiteDatabase bancoDados){
        ArrayList<Livro> livroArrayList = new ArrayList<>();
        String[] colunas = new String[]{COLUMN_ID,COLUMN_TITULO_LIVRO,COLUMN_QTDCAPITULOS,COLUMN_ABREVIACAO};
        cursor = bancoDados.query(TABLE_LIVROS,colunas, null,null,null,null,null);
        Livro livro;
        while(cursor.moveToNext()){
            livro = new Livro();
            livro.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
            livro.setTituloLivro(cursor.getString(cursor.getColumnIndex(COLUMN_TITULO_LIVRO)));
            livro.setAbreviacao(cursor.getString(cursor.getColumnIndex(COLUMN_ABREVIACAO)));
            livro.setQtdCapitulos(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_QTDCAPITULOS))));
            livroArrayList.add(livro);
        }
        bancoDados.close();
        return livroArrayList;
    }

    /**
     * Metodo Busca os titulos e Lista para ler na view
     */
    public List<Versiculo> getCapitulos(SQLiteDatabase bancoDados,String TABLE_CAPITULO){
        ArrayList<Versiculo> versiculoArrayList = new ArrayList<>();
        cursor = bancoDados.query(TABLE_CAPITULO, new String[]{COLUMN_ID,COLUMN_VERSICULO}, null,null,null,null,null);
        Versiculo versiculo =null;
        while(cursor.moveToNext()){
            versiculo = new Versiculo();
            versiculo.setId(Long.parseLong(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
            versiculo.setTexto(cursor.getString(cursor.getColumnIndex(COLUMN_VERSICULO)));
            versiculoArrayList.add(versiculo);
        }
        bancoDados.close();
        return versiculoArrayList;
    }



    /**
     * Recupera todoas as marcacoes realizadas em um capitulo especifico.
     * @param idLivro
     * @param idNumCap
     * @return
     */

    public List<Marcacoes> recuperaMarcacoes(SQLiteDatabase bancoDados,int idLivro,int idNumCap){
        List<Marcacoes> marcacoesArrayList = new ArrayList<>();

        String query =  COLUMN_IDLIVRO+" = ? AND "+COLUMN_IDNUMCAP +" = ?";
        String[] args = {""+idLivro,""+idNumCap};
        String[] columns = new String[]{COLUMN_ID,COLUMN_IDLIVRO,COLUMN_IDNUMCAP,COLUMN_VERSICULO,COLUMN_SUBLINHADO,COLUMN_MARCACAO_COLOR,COLUMN_FAVORITO,COLUMN_IDANOTACOES};
        cursor = bancoDados.query(TABLE_MARCACOES,columns,query,args,null,null,null);
        Marcacoes marcacoes =null;
        while(cursor.moveToNext()){
            marcacoes = new Marcacoes();
            marcacoes.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
            marcacoes.setIdLivro(idLivro);
            marcacoes.setIdNumCap(idNumCap);
            marcacoes.setIdVersiculo(cursor.getInt(cursor.getColumnIndex(COLUMN_VERSICULO)));
            marcacoes.setSublinhado(cursor.getInt(cursor.getColumnIndex(COLUMN_SUBLINHADO)) > 0);
            marcacoes.setMarcacao_color(cursor.getInt(cursor.getColumnIndex(COLUMN_MARCACAO_COLOR)));
            marcacoes.setFavorito(cursor.getInt(cursor.getColumnIndex(COLUMN_FAVORITO)) > 0);
            String anotacao  = cursor.getString(cursor.getColumnIndex(COLUMN_IDANOTACOES));
            if(anotacao!=null) {
                marcacoes.setIdAnotacoes(Integer.parseInt(anotacao));
            }
            marcacoesArrayList.add(marcacoes);
        }
        bancoDados.close();
        return  marcacoesArrayList;
    }

    /**
     * Método que Deleta as marcações selecionadas no texto
     * @param bancoDados
     * @param idLivro
     * @param idNumCap
     * @param idsVersiculos
     */
    public void deletMarcacoes(SQLiteDatabase bancoDados,int idLivro,int idNumCap,Integer... idsVersiculos){
        for (Integer idVersiculo:idsVersiculos) {
            String query = COLUMN_IDLIVRO + " = ? AND " + COLUMN_IDNUMCAP + " = ? AND " + COLUMN_VERSICULO + " = ?";
            String[] args = {"" + idLivro, "" + idNumCap, "" + idVersiculo};
            String[] columns = new String[]{COLUMN_ID, COLUMN_IDLIVRO, COLUMN_IDNUMCAP, COLUMN_VERSICULO, COLUMN_SUBLINHADO, COLUMN_MARCACAO_COLOR, COLUMN_FAVORITO};
            bancoDados.delete(TABLE_MARCACOES, query, args);
        }
        bancoDados.close();
    }

    /**
     * Salva ou Atualiza as Marcações dos textos selecionados
     * @param idLivro
     * @param idNumCap
     * @param marcacoes
     */
    public void salvarOrUpdateMarcarcoes(int idLivro,int idNumCap, List<Marcacoes> marcacoes){
       List<Marcacoes> marcacoesExistentes = recuperaMarcacoes(this.openDB(),idLivro,idNumCap);
        this.openDB();
        ContentValues values;

        if(marcacoesExistentes.size()!=0) {
            for(Marcacoes marc : marcacoes) {
                for (Marcacoes list : marcacoesExistentes) {
                    values = new ContentValues();
                    if (list.getIdVersiculo() == marc.getIdVersiculo()) {
                        if(marc.getSublinhado()!= null){
                            values.put(COLUMN_SUBLINHADO,marc.getSublinhado());
                        }
                        if(marc.getMarcacao_color()!=null){
                            values.put(COLUMN_MARCACAO_COLOR,marc.getMarcacao_color());
                        }
                        if(marc.getFavorito()!=null){
                            values.put(COLUMN_FAVORITO,marc.getFavorito());
                        }
                        if(marc.getIdAnotacoes()!=null){
                            values.put(COLUMN_IDANOTACOES,marc.getIdAnotacoes());
                        }

                        bancoDados.update(TABLE_MARCACOES, values,COLUMN_ID +"= ?",new String[]{""+list.getId()});
                    }
                }
            }
            marcacoes.removeAll(marcacoesExistentes);
        }

        for(Marcacoes marc : marcacoes){
            values = new ContentValues();
            values.put(COLUMN_IDLIVRO, marc.getIdLivro());
            values.put(COLUMN_IDNUMCAP,marc.getIdNumCap());
            values.put(COLUMN_VERSICULO,marc.getIdVersiculo());
            values.put(COLUMN_SUBLINHADO,marc.getSublinhado());
            values.put(COLUMN_MARCACAO_COLOR,marc.getMarcacao_color());
            values.put(COLUMN_FAVORITO, marc.getFavorito());
            values.put(COLUMN_IDANOTACOES, marc.getIdAnotacoes());
            if(!bancoDados.isOpen()){
                this.openDB();
            }

            bancoDados.insert(TABLE_MARCACOES, null, values);
        }
        bancoDados.close();
    }

    public List<ItemFavorito> recuperaListaFavoritos(SQLiteDatabase bancoDados) {

        List<ItemFavorito> itemFavoritos = new ArrayList<>();
        String query = COLUMN_FAVORITO + " = ?";
        String[] args = {"" + 1};
        String[] columns = new String[]{COLUMN_ID, COLUMN_IDLIVRO, COLUMN_IDNUMCAP, COLUMN_VERSICULO, COLUMN_SUBLINHADO, COLUMN_MARCACAO_COLOR, COLUMN_FAVORITO};
        cursor = bancoDados.query(TABLE_MARCACOES, columns, query, args, null, null, null);
        ItemFavorito item = null;
        while (cursor.moveToNext()) {
            item = new ItemFavorito();
            item.setIdLivro(cursor.getInt(cursor.getColumnIndex(COLUMN_IDLIVRO)));
            item.setIdCapitulo(cursor.getInt(cursor.getColumnIndex(COLUMN_IDNUMCAP)));
            item.setIdVersiculo(cursor.getInt(cursor.getColumnIndex(COLUMN_VERSICULO)));
            itemFavoritos.add(item);
        }

        for (ItemFavorito items : itemFavoritos) {
            String query2 = COLUMN_IDLIVRO + " = ? AND " + COLUMN_IDNUMCAP + " = ?";
            String[] args2 = {"" + items.getIdLivro(), "" + items.getIdCapitulo()};
            cursor = bancoDados.query(TABLE_RELAC_LIVRO_CAP, new String[]{COLUMN_ID, COLUMN_IDLIVRO, COLUMN_IDNUMCAP, COLUMN_NOME_TABELA}, query2, args2, null, null, null);
            while (cursor.moveToNext()) {
                items.setTitulo(cursor.getString(cursor.getColumnIndex(COLUMN_NOME_TABELA)));
                break;
            }
        }

        for (ItemFavorito items : itemFavoritos) {
            String query2 = COLUMN_ID + " = ?";
            String[] args2 = {"" + items.getIdVersiculo()};
            cursor = bancoDados.query(items.getTitulo(), new String[]{COLUMN_ID, COLUMN_VERSICULO}, query2, args2, null, null, null);
            while (cursor.moveToNext()) {
                items.setTexto(cursor.getString(cursor.getColumnIndex(COLUMN_VERSICULO)));
            }
        }

        for (ItemFavorito items : itemFavoritos) {
            String query2 = COLUMN_ID + " = ?";
            String[] args2 = {"" + items.getIdLivro()};
            cursor = bancoDados.query(TABLE_LIVROS, new String[]{COLUMN_ID, COLUMN_TITULO_LIVRO, COLUMN_QTDCAPITULOS, COLUMN_ABREVIACAO}, query2, args2, null, null, null);
            while (cursor.moveToNext()) {
                items.setTitulo(cursor.getString(cursor.getColumnIndex(COLUMN_ABREVIACAO)) + " Cap: " + items.getIdCapitulo() + " Ver: " + items.getIdVersiculo());
            }
        }
        bancoDados.close();
        Collections.reverse(itemFavoritos);
        return itemFavoritos;
    }


    /**
     * Cria o banco de dados se não existe
     * @param bancoDados
     */
    @Override
    public void onCreate(SQLiteDatabase bancoDados) {
        bancoDados.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase bancoDados, int oldVersion, int newVersion) {
        onCreate(bancoDados);
    }

    /**
     * Abre conexao
     * @return
     */
    public SQLiteDatabase openDB(){
        try{
            bancoDados = contextStatic.openOrCreateDatabase(PersistenceDao.DATABASE_NAME, Context.MODE_PRIVATE, null);
        }catch (Exception e){
        }
        return bancoDados;
    }

    /**
     * Abre conexao
     * @return
     */
    public SQLiteDatabase openDB(Context context){
        try{
            bancoDados = context.openOrCreateDatabase(PersistenceDao.DATABASE_NAME, Context.MODE_PRIVATE, null);
        }catch (Exception e){
        }
        return bancoDados;
    }



    public void copiaBanco(String dataBaseName) {
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            // Abre o arquivo que deve estar na pasta assets
            is = contextStatic.getAssets().open(dataBaseName);
            // Abre o arquivo do banco vazio ele fica em:
            // /data/data/nome.do.pacote.da.app/databases
            fos = new FileOutputStream(contextStatic.getDatabasePath(dataBaseName));
            byte[] buffer = new byte[1024];
            int read;
            while ((read = is.read(buffer)) > 0) {
                fos.write(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean getExiteBase(String dataBaseName){
        return contextStatic.getDatabasePath(dataBaseName).exists();
    }
    public boolean getDeleteBase(String dataBaseName){
        return contextStatic.getDatabasePath(dataBaseName).delete();
    }


    /**
     * Sava as preferencias em um arquivo de Preferences interno do Android
     * @param idLivro
     * @param idCapitulo
     */
    public void salvarEstadoPreferences(int idLivro,int idCapitulo){
        SharedPreferences settings = contextStatic.getSharedPreferences("Preferences", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("idlivro", idLivro);
        editor.putInt("idcapitulo", idCapitulo);
        editor.commit();
    }

    public int getEstadoLivroPreferences(){
        SharedPreferences settings = contextStatic.getSharedPreferences("Preferences", 0);
        return settings.getInt("idlivro", 1);
    }
    public int getEstadoCapituloPreferences(){
        SharedPreferences settings = contextStatic.getSharedPreferences("Preferences", 0);
        return settings.getInt("idcapitulo", 1);
    }


}