package br.com.umservodemaria.napalavra.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.umservodemaria.napalavra.adapter.CardAdapter;
import br.com.umservodemaria.napalavra.dao.PersistenceDao;
import br.com.umservodemaria.napalavra.vo.ItemFavorito;
import br.com.umservodemaria.napalavra.R;

public class FavoritosActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener, View.OnClickListener{
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private PersistenceDao persistenceDao;
    private GestureDetectorCompat gestureDetector;
    private List<ItemFavorito> itemsfavoritos;
    private SparseBooleanArray selectedItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        persistenceDao = new PersistenceDao(this);
        selectedItems = new SparseBooleanArray();

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view_favoritos);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView .addOnItemTouchListener(this);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        itemsfavoritos = persistenceDao.recuperaListaFavoritos(persistenceDao.openDB());
        mAdapter = new CardAdapter(itemsfavoritos);
        mRecyclerView.setAdapter(mAdapter);
        gestureDetector = new GestureDetectorCompat(this,new RecyclerViewOnGestureListener());

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        clearSelections();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
       ItemFavorito item = itemsfavoritos.get(v.getId());
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("idLivro",item.getIdLivro());
        intent.putExtra("idCapitulo",item.getIdCapitulo());
        intent.putExtra("idVersiculo", item.getIdVersiculo());
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
       gestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            int idx = mRecyclerView.getChildAdapterPosition(view);
            view.setId(idx);
            onClick(view);
            return super.onSingleTapConfirmed(e);
        }

        public void onLongPress(MotionEvent e) {
            View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            int idx = mRecyclerView.getChildAdapterPosition(view);
            toggleSelection(idx);
            onSelectRecyclerView(view);
            super.onLongPress(e);
        }
    }

    public void onSelectRecyclerView(View view){
        

    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }
    }

    public void clearSelections() {
        selectedItems.clear();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<Integer>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }
}
