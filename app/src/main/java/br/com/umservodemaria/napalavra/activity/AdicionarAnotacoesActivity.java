package br.com.umservodemaria.napalavra.activity;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import br.com.umservodemaria.napalavra.R;
import br.com.umservodemaria.napalavra.vo.Constantes;


public class AdicionarAnotacoesActivity extends AppCompatActivity {
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_anotacoes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String anotacoes = getIntent().getStringExtra(Constantes.ANOTACOES);
        String tituloAnotacoes = getIntent().getStringExtra(Constantes.TITULO_ANOTACOES);

        TextView textViewAnotacoes = (TextView) findViewById(R.id.id_cv_texto_anotacoes);
        TextView textViewTitulo = (TextView) findViewById(R.id.id_cv_title_anotacoes);

        textViewTitulo.setText(tituloAnotacoes);
        textViewAnotacoes.setText(anotacoes);

        fabAdd =  (FloatingActionButton) findViewById(R.id.fabAddAnotacoes);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarAnotacao();
            }
        });
    }

    private void salvarAnotacao(){


    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdicionarAnotacoesActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        super.onBackPressed();
    }

}
