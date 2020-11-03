package br.com.umservodemaria.napalavra.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import br.com.umservodemaria.napalavra.R;
import br.com.umservodemaria.napalavra.vo.ItemData;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ItemData[] itemsData;
    private SparseBooleanArray selectedItems;


    public MyAdapter(ItemData[] itemsData) {
        this.itemsData = itemsData;
        selectedItems = new SparseBooleanArray();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.txtViewTitle.setText(itemsData[position].getTexto());

        boolean controlaSelecao = false;
        if(itemsData[position].getSublinhado()!=null && itemsData[position].getSublinhado()) {
            String caption = viewHolder.txtViewTitle.getText().toString();
            // Método que sublinha a linha no textview
            SpannableString str = new SpannableString(caption);
            str.setSpan(new UnderlineSpan(), 0, caption.length(), 0);
            viewHolder.txtViewTitle.setText(str);
            controlaSelecao =true;
        }
        if(itemsData[position].getMarcacao_color() !=null && itemsData[position].getMarcacao_color()>0){
            viewHolder.itemView.setBackgroundResource(itemsData[position].getMarcacao_color());
            viewHolder.txtViewTitle.setTextColor(Color.BLACK);
            controlaSelecao = true;
        }

        if(itemsData[position].getFavorito() !=null && itemsData[position].getFavorito()){
            viewHolder.txtViewTitle.setTextColor(Color.RED);
            viewHolder.txtViewTitle.setTypeface(null, Typeface.ITALIC);
            controlaSelecao = true;
        }


        if(selectedItems.get(position, false)) {
            //Código que marca o texto
            viewHolder.itemView.setBackgroundResource(R.color.marca_texto);
            viewHolder.txtViewTitle.setTextColor(Color.WHITE);
        }else if(!controlaSelecao){
            viewHolder.itemView.setBackgroundResource(R.color.grey100);
            viewHolder.txtViewTitle.setTextColor(Color.BLACK);
        }
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.length;
    }
    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        }
        else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }
    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtViewTitle;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
        }

    }

    public CharSequence getItemsData(int i) {
        return itemsData[i].getTexto();
    }

    public void setSelectedItems(SparseBooleanArray selectedItems) {
        this.selectedItems = selectedItems;
    }
    public boolean getSelectedItems(int i) {
        return selectedItems.get(i);
    }
}