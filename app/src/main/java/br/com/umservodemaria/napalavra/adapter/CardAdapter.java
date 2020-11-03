package br.com.umservodemaria.napalavra.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.umservodemaria.napalavra.R;
import br.com.umservodemaria.napalavra.vo.ItemFavorito;

/**
 * Created by cleiton.dantas on 01/12/2015.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{
    List<ItemFavorito> itemsfavoritos;

    public CardAdapter(List<ItemFavorito> itemsfavoritos){
        super();
        this.itemsfavoritos = itemsfavoritos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_card_item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.titulo.setText(itemsfavoritos.get(i).getTitulo());
        viewHolder.texto.setText(itemsfavoritos.get(i).getTexto());



    }

    @Override
    public int getItemCount() {
        return itemsfavoritos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titulo;
        public TextView texto;

        public ViewHolder(View itemView) {
            super(itemView);
            titulo = (TextView)itemView.findViewById(R.id.id_cv_title_fav);
            texto = (TextView)itemView.findViewById(R.id.id_cv_texto_fav);
        }


    }
}
