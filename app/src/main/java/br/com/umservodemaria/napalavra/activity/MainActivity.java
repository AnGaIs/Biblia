package br.com.umservodemaria.napalavra.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ExpandableListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import  android.view.animation.Animation;
import static android.view.GestureDetector.SimpleOnGestureListener;

import br.com.umservodemaria.napalavra.vo.Constantes;
import br.com.umservodemaria.napalavra.R;
import br.com.umservodemaria.napalavra.adapter.MyAdapter;
import br.com.umservodemaria.napalavra.adapter.MyExpandableAdapter;
import br.com.umservodemaria.napalavra.adapter.MyRecyclerScroll;
import br.com.umservodemaria.napalavra.bo.MainBo;
import br.com.umservodemaria.napalavra.dao.PersistenceDao;
import br.com.umservodemaria.napalavra.vo.ItemData;
import br.com.umservodemaria.napalavra.vo.Livro;
import br.com.umservodemaria.napalavra.vo.Marcacoes;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,RecyclerView.OnItemTouchListener, View.OnClickListener {

    private ArrayList<Livro> livroArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ExpandableListView expandableList;
    private MyExpandableAdapter myExpandAdapter;
    private DrawerLayout drawer;
    private GestureDetectorCompat gestureDetector;
    private MyAdapter adapter =null;
    private PersistenceDao persistenceDao = new PersistenceDao(this);
    private FloatingActionButton fab,fab2, fabFavorito,fabShare,fabAnotacao,fabMark,fabMarkColor1,fabMarkColor2,fabMarkColor3,fabVoltarCap,fabAvancaCap;
    private FloatingActionButton[] allFloatingActionButtons;
    private Animation animeFloating,animeFloating2;
    private int idLivro, idCapitulo;
    private ItemData itemsData[];
    private NavigationView navigationView;
    private MainBo mainBo = new MainBo();
    private List<Livro> livroList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Instancia as Variaveis
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        expandableList = (ExpandableListView) findViewById(R.id.id_expandable_listView);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        fab =  (FloatingActionButton) findViewById(R.id.fab);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fabFavorito = (FloatingActionButton) findViewById(R.id.fab_favorito);
        fabShare = (FloatingActionButton) findViewById(R.id.idfab_share);
        fabAnotacao = (FloatingActionButton) findViewById(R.id.fab_anotacao);
        fabMark = (FloatingActionButton) findViewById(R.id.fab_mark);

        fabMarkColor1 = (FloatingActionButton) findViewById(R.id.fab_color1);
        fabMarkColor2 = (FloatingActionButton) findViewById(R.id.fab_color2);
        fabMarkColor3 = (FloatingActionButton) findViewById(R.id.fab_color3);

        fabVoltarCap = (FloatingActionButton) findViewById(R.id.fab_avancar_cap);
        fabAvancaCap = (FloatingActionButton) findViewById(R.id.fab_voltar_cap);

        animeFloating = AnimationUtils.loadAnimation(this, R.anim.rotate);
        animeFloating2 = AnimationUtils.loadAnimation(this, R.anim.rotate2);
        allFloatingActionButtons = new FloatingActionButton[]{fab,fab2,fabAnotacao, fabFavorito,fabShare,fabMark,fabMarkColor1,fabMarkColor2,fabMarkColor3};
        //Da um Hide em todos os elementos
        hideAllFab(allFloatingActionButtons);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(this);
        gestureDetector = new GestureDetectorCompat(this, new RecyclerViewOnGestureListener());

        persistenceDao = new PersistenceDao(this.getApplicationContext());
      // persistenceDao.getDeleteBase(PersistenceDao.DATABASE_NAME);
        if(!persistenceDao.getExiteBase(PersistenceDao.DATABASE_NAME)) {
            persistenceDao.openDB();
            persistenceDao.copiaBanco(PersistenceDao.DATABASE_NAME);
        }

       livroList = persistenceDao.getLivros(persistenceDao.openDB());
        livroArrayList.addAll(livroList);
        mainBo.populaLivroList(livroArrayList);

        myExpandAdapter = new MyExpandableAdapter(livroArrayList, this);
        myExpandAdapter.setInflater((android.view.LayoutInflater) this.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE), this);
        expandableList.setDividerHeight(3);
        expandableList.setChildDivider(getResources().getDrawable(R.color.background_material_dark));
        expandableList.setAdapter(myExpandAdapter);

        //Verifica se a chamada da tela está sendo nova ou se está vindo de Favoritos
        Intent intent = getIntent();
        int idExtraLivro = intent.getIntExtra("idLivro", 1000);
        int idExtraCapitulo = intent.getIntExtra("idCapitulo",1000);
        int idExtraVersiculo = intent.getIntExtra("idVersiculo",1000);

        if(idExtraCapitulo!=1000 && idExtraLivro!=1000){
            this.idCapitulo = idExtraCapitulo;
            this.idLivro = idExtraLivro;
        }else{
            //Instancio a Primeira pagina
            this.idLivro = persistenceDao.getEstadoLivroPreferences();
            this.idCapitulo = persistenceDao.getEstadoCapituloPreferences();
        }

        itemsData = mainBo.recuperaVersiculosSelecionados(persistenceDao, idLivro, idCapitulo, livroArrayList);
        setTitle(mainBo.getTitle());
        createView(recyclerView, itemsData);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                drawer.closeDrawers();
                Handler handler = new Handler();
                switch (menuItem.getItemId()) {
                    case R.id.idFavoritos:
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(MainActivity.this, FavoritosActivity.class);
                                startActivity(intent);
                                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            }
                        }, 400);
                        break;
                    case R.id.idAnotacoes:
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(MainActivity.this, AnotacoesActivity.class);
                                startActivity(intent);
                                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            }
                        }, 400);
                        break;
                }
                return false;
            }
        });

        recyclerView.setOnScrollListener(new MyRecyclerScroll() {
            @Override
            public void show() {
                showAllAnimateFab(fabVoltarCap, fabAvancaCap);
            }

            @Override
            public void hide() {
                hideAllAnimateFab(fabVoltarCap, fabAvancaCap);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (View.GONE == fab2.getVisibility()) {
                    fab.startAnimation(animeFloating);
                    showAllFab(fab2, fabFavorito, fabAnotacao, fabShare);
                    fab2.animate().translationY(0).setInterpolator(new DecelerateInterpolator(5)).setStartDelay(50);
                    fabFavorito.animate().translationY(0).setInterpolator(new DecelerateInterpolator(5)).setStartDelay(100);
                    fabAnotacao.animate().translationY(0).setInterpolator(new DecelerateInterpolator(5)).setStartDelay(100);
                    fabShare.animate().translationY(0).setInterpolator(new DecelerateInterpolator(5)).setStartDelay(150);

                } else {
                    fab.startAnimation(animeFloating2);
                    fab2.animate().translationY(fab.getHeight() + 100).setInterpolator(new AccelerateInterpolator(3)).start();
                    fabFavorito.animate().translationY(fab2.getHeight() + 100).setInterpolator(new AccelerateInterpolator(3)).start();
                    fabAnotacao.animate().translationY(fab2.getHeight() + 100).setInterpolator(new AccelerateInterpolator(3)).start();
                    fabShare.animate().translationY(fabFavorito.getHeight() + 100).setInterpolator(new AccelerateInterpolator(3)).start();
                    hideAllFab(fab2, fabFavorito,fabAnotacao, fabShare, fabMark,  fabMarkColor1, fabMarkColor2, fabMarkColor3);

                }
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (View.GONE == fabMark.getVisibility()) {
                    showAllFab(fabMark,  fabMarkColor1, fabMarkColor2, fabMarkColor3);
                    hideAllFab(fabFavorito,fabAnotacao, fabShare);
                } else {
                    showAllFab(fabFavorito,fabAnotacao,  fabShare);
                    hideAllFab(fabMark, fabMarkColor1, fabMarkColor2, fabMarkColor3);
                }
            }
        });
        //Marca Como Favorito
        fabFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marcaComoFavorito();
                moveScroll();
                setTitle(mainBo.getTitle());
                showAllFab(fabVoltarCap, fabAvancaCap);
                hideAllFab(allFloatingActionButtons);
            }
        });

        fabMarkColor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizaMarcacao(R.color.colorAccent);
                moveScroll();
                setTitle(mainBo.getTitle());
                showAllFab(fabVoltarCap, fabAvancaCap);
                hideAllFab(allFloatingActionButtons);
            }
        });
        fabMarkColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getSelectedItems().size()!=0) {
                    realizaMarcacao(R.color.verde_florecente);
                    moveScroll();
                    setTitle(mainBo.getTitle());
                    showAllFab(fabVoltarCap, fabAvancaCap);
                    hideAllFab(allFloatingActionButtons);
                }
            }
        });
        fabMarkColor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getSelectedItems().size() != 0) {
                    realizaMarcacao(R.color.roxo_florecente);
                    moveScroll();
                    setTitle(mainBo.getTitle());
                    showAllFab(fabVoltarCap, fabAvancaCap);
                    hideAllFab(allFloatingActionButtons);

                }
            }
        });
        fabMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getSelectedItems().size() != 0) {
                    deletaMarcaoes();
                    moveScroll();
                    setTitle(mainBo.getTitle());
                    showAllFab(fabVoltarCap, fabAvancaCap);
                    hideAllFab(allFloatingActionButtons);
                }
            }
        });
//        fabSublinhe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (adapter.getSelectedItems().size() != 0) {
//                    realizaMarcacaoSublinhado();
//                    moveScroll();
//                    setTitle(mainBo.getTitle());
//                    showAllFab(fabVoltarCap, fabAvancaCap);
//                    hideAllFab(allFloatingActionButtons);
//                }
//            }
//        });

        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getSelectedItems().size() != 0) {
                    compartilharShare();
                    setTitle(mainBo.getTitle());
                    showAllFab(fabVoltarCap, fabAvancaCap);
                    hideAllFab(allFloatingActionButtons);
                }

            }
        });

        fabAnotacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                if (adapter.getSelectedItems().size() != 0) {
                    moveScroll();
                    setTitle(mainBo.getTitle());
                    showAllFab(fabVoltarCap, fabAvancaCap);
                    hideAllFab(allFloatingActionButtons);
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, AdicionarAnotacoesActivity.class);
                            Bundle dados = new Bundle();
                            List<Marcacoes> listMarcacoes = mainBo.marcacoesEdit(adapter, idLivro, idCapitulo, null, null, null, true);
                            Livro livro = livroList.get(livroList.indexOf(new Livro(idLivro)));
                            String shareTitleVerConcat= livro.getAbreviacao()+" "+ livro.getCapituloList().get(idCapitulo-1).getTitulo()+", ";
                            String shareAnotacoes = "";
                            for (Marcacoes item: listMarcacoes){
                                shareTitleVerConcat = shareTitleVerConcat.concat(item.getIdVersiculo()+".");
                                shareAnotacoes = shareAnotacoes.concat(System.getProperty("line.separator")+ itemsData[item.getIdVersiculo()-1].toString());
                            }
                            dados.putString(Constantes.TITULO_ANOTACOES,shareTitleVerConcat);
                            dados.putString(Constantes.ANOTACOES,shareAnotacoes);
                            intent.putExtras(dados);
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        }
                    }, 200);
                }
            }
        });

        fabAvancaCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((idCapitulo+1)>livroArrayList.get(idLivro-1).getCapituloList().size()){
                    return;
                }
                mudaCapitulo(idCapitulo+=1);
            }
        });

        fabVoltarCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((idCapitulo-1)<=0){
                    return;
                }
                mudaCapitulo(idCapitulo-=1);
            }
        });
    }
    private void moveScroll(){
        LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
        if(MyRecyclerScroll.getDisplayedposition()<=itemsData.length){
            llm.scrollToPositionWithOffset((MyRecyclerScroll.getDisplayedposition()+1), itemsData.length);
        }
    }

    public void realizaMarcacao(int R_Color){
        persistenceDao.salvarOrUpdateMarcarcoes(idLivro, idCapitulo, mainBo.marcacoesEdit(adapter,idLivro,idCapitulo,R_Color,null,null,null));
        itemsData = mainBo.recuperaVersiculosSelecionados(persistenceDao,idLivro,idCapitulo,livroArrayList);
        setTitle(mainBo.getTitle());
        createView(recyclerView, itemsData);
    }

    public void realizaMarcacaoSublinhado(){
        persistenceDao.salvarOrUpdateMarcarcoes(idLivro, idCapitulo, mainBo.marcacoesEdit(adapter, idLivro, idCapitulo, null, null, true,null));
        itemsData = mainBo.recuperaVersiculosSelecionados(persistenceDao,idLivro,idCapitulo,livroArrayList);
        setTitle(mainBo.getTitle());
        createView(recyclerView, itemsData);
    }
    public void compartilharShare(){
        List<Marcacoes> listMarcacoes = mainBo.marcacoesEdit(adapter, idLivro, idCapitulo, null, null, null, true);

        Livro livro = livroList.get(livroList.indexOf(new Livro(idLivro)));
        String shareTextVerConcat= livro.getTituloLivro()+" "+ livro.getCapituloList().get(idCapitulo-1).getTitulo()+""+ System.getProperty("line.separator");

        for (Marcacoes item: listMarcacoes){
            shareTextVerConcat = shareTextVerConcat.concat(System.getProperty("line.separator")+ itemsData[item.getIdVersiculo()-1].toString());
        }
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,shareTextVerConcat);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
    public void mudaCapitulo(int idCapitulo){
        persistenceDao.salvarEstadoPreferences(idLivro,idCapitulo);
        itemsData = mainBo.recuperaVersiculosSelecionados(persistenceDao,idLivro,idCapitulo,livroArrayList);
        setTitle(mainBo.getTitle());
        createView(recyclerView, itemsData);
    }



    public void marcaComoFavorito(){
        if(adapter.getSelectedItems().size()==0) {
            Toast.makeText(this, R.string.selecione_versiculo, Toast.LENGTH_SHORT).show();
            return;
        }
        if(adapter.getSelectedItems().size()>1) {
            Toast.makeText(this, R.string.selecione_apenas_um_versiculo, Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this,R.string.favorito_salvo, Toast.LENGTH_SHORT).show();
        persistenceDao.salvarOrUpdateMarcarcoes(idLivro, idCapitulo, mainBo.marcacoesEdit(adapter, idLivro, idCapitulo, null, true, null,null));
        itemsData = mainBo.recuperaVersiculosSelecionados(persistenceDao,idLivro,idCapitulo,livroArrayList);
        setTitle(mainBo.getTitle());
        createView(recyclerView, itemsData);
    }

    public void deletaMarcaoes(){
       Integer[] vers = new Integer[adapter.getSelectedItems().size()];
        for (int i=0;i<adapter.getSelectedItems().size();i++) {
            vers[i] = (adapter.getSelectedItems().get(i)+1);
        }
        persistenceDao.deletMarcacoes(persistenceDao.openDB(), idLivro, idCapitulo, vers);

        itemsData = mainBo.recuperaVersiculosSelecionados(persistenceDao,idLivro,idCapitulo,livroArrayList);
        setTitle(mainBo.getTitle());
        createView(recyclerView, itemsData);
    }

    private void myToggleSelection(int idx) {
        adapter.toggleSelection(idx);
        Integer  itemCont  =  adapter.getSelectedItemCount();
        String title = getString(R.string.selected_count,(Object)itemCont);
        setTitle(title);
        if(adapter.getSelectedItemCount()==0){
            setTitle(mainBo.getTitle());
            showAllFab(fabVoltarCap, fabAvancaCap);
            hideAllFab(allFloatingActionButtons);
        }else{
            showAllFab(fab);
            showAllAnimateFab(fab);
            hideAllFab(fabAvancaCap,fabVoltarCap);
        }
    }


    // Cria a visualização da lista
    public void createView(RecyclerView recyclerView, ItemData[] itemsData) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(itemsData);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setClickable(true);
    }



    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (adapter.getSelectedItemCount()!=0){
                adapter.clearSelections();
                setTitle(mainBo.getTitle());
                showAllFab(fabVoltarCap, fabAvancaCap);
                hideAllFab(allFloatingActionButtons);
                return;
            }
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Handler handler = new Handler();
        int id = item.getItemId();
        if (id == R.id.action_settings) {
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }
                }, 400);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.action_bar) {

        } else if (v.getId() == R.id.container_list_item) {
            int idx = recyclerView.getChildAdapterPosition(v);
            if (adapter.getSelectedItemCount() >0) {
                myToggleSelection(idx);
                return;
            }
        }
    }

    //Métodos de Apoio
    /**
     *  Chama o Hide de todos dos FloatingActionButton Passados
     */
    private void hideAllFab(FloatingActionButton... fabsArray){
        for (int i=0;i<fabsArray.length;i++){
            fabsArray[i].hide();
        }
    }

    private void showAllFab(FloatingActionButton... fabsArray){
        for (int i=0;i<fabsArray.length;i++){
            fabsArray[i].show();
        }
    }
    private void showAllAnimateFab(FloatingActionButton... fabsArray){
        for (int i=0;i<fabsArray.length;i++){
            fabsArray[i].animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
        }
    }
    private void hideAllAnimateFab(FloatingActionButton... fabsArray){
        for (int i=0;i<fabsArray.length;i++){
            fabsArray[i].animate().translationY(fab.getHeight() + 200).setInterpolator(new AccelerateInterpolator(2)).start();
        }
    }
    private class RecyclerViewOnGestureListener extends SimpleOnGestureListener{

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            view.setId(R.id.container_list_item);
            onClick(view);
            return super.onSingleTapConfirmed(e);
        }

        public void onLongPress(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            int idx = recyclerView.getChildAdapterPosition(view);
            myToggleSelection(idx);
            super.onLongPress(e);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public PersistenceDao getPersistenceDao() {
        return persistenceDao;
    }

    // Métodos do Import
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }

    public MainBo getMainBo() {
        return mainBo;
    }

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
}
