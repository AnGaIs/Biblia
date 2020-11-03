package br.com.umservodemaria.napalavra.adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.umservodemaria.napalavra.activity.MainActivity;
import br.com.umservodemaria.napalavra.R;
import br.com.umservodemaria.napalavra.dao.PersistenceDao;
import br.com.umservodemaria.napalavra.vo.Capitulo;
import br.com.umservodemaria.napalavra.vo.ItemData;
import br.com.umservodemaria.napalavra.vo.Livro;

public class MyExpandableAdapter extends CustomBaseExpandableListAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<Livro> livroArrayList;
    private List<Capitulo> childCapitulos;
    private MainActivity mainActivity;
    private TextView textView = null;
    private PersistenceDao persistenceDao;
    private int idLivro,idCapitulo;
    private ItemData[] itemsData;

    public MyExpandableAdapter(ArrayList<Livro> livros, MainActivity mainActivity) {
        this.livroArrayList = livros;
        this.mainActivity = mainActivity;
        persistenceDao = mainActivity.getPersistenceDao();
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        childCapitulos = livroArrayList.get(groupPosition).getCapituloList();
        //Posição id do Livro e do Capitulo selecionado
        idLivro = livroArrayList.get(groupPosition).getId();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.group, null);
        }

        textView = (TextView) convertView.findViewById(R.id.tvItemGrupo);
        textView.setText(childCapitulos.get(childPosition).getTitulo());

        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity,livroArrayList.get(groupPosition).getTituloLivro() +" "+ childCapitulos.get(childPosition).getTitulo(), Toast.LENGTH_SHORT).show();
                idCapitulo = childCapitulos.get(childPosition).getId();
                mainActivity.setIdLivro(idLivro);
                mainActivity.setIdCapitulo((idCapitulo+1));

                persistenceDao.salvarEstadoPreferences(idLivro, (idCapitulo+1));
                itemsData = mainActivity.getMainBo().recuperaVersiculosSelecionados(persistenceDao, idLivro, (idCapitulo + 1), livroArrayList);
                mainActivity.createView(mainActivity.getRecyclerView(), itemsData);

                activity.setTitle(livroArrayList.get(groupPosition).getAbreviacao() +" "+ childCapitulos.get(childPosition).getTitulo());
                mainActivity.getDrawer().closeDrawers();
            }
        });
        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
        }
        ((CheckedTextView) convertView).setText(livroArrayList.get(groupPosition).getTituloLivro());
        ((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return  livroArrayList.get(groupPosition).getCapituloList().size();
    }

    @Override
    public int getGroupCount() {
        return livroArrayList.size();
    }

}