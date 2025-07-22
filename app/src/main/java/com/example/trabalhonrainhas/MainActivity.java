package com.example.trabalhonrainhas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private TabuleiroRainhas jogo;

    private GridLayout gradeTabuleiro;
    private TextView textoMensagem;
    private Button botaoReiniciar;

    private ImageButton botaoConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        jogo = new TabuleiroRainhas(4);





        gradeTabuleiro = findViewById(R.id.boardGrid);
        textoMensagem = findViewById(R.id.messageText);
        botaoReiniciar = findViewById(R.id.resetButton);
        botaoConfig = findViewById(R.id.botaoConfig);



        botaoReiniciar.setOnClickListener(v -> {
            jogo.reiniciar(jogo.getTamanho());
            desenharTabuleiro();
        });

        desenharTabuleiro();

        botaoConfig.setOnClickListener(v ->{
            Intent intencao = new Intent(MainActivity.this,ConfigActivity.class);
            startActivity(intencao);

        });
    }



    private void desenharTabuleiro() {
        gradeTabuleiro.removeAllViews();
        gradeTabuleiro.setColumnCount(jogo.getTamanho());
        gradeTabuleiro.setRowCount(jogo.getTamanho());

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int dimensaoCelula = Math.min(screenHeight,screenWidth);
        int tamanhoCelula = (dimensaoCelula - 100) / jogo.getTamanho();

        if(screenHeight < screenWidth)
            tamanhoCelula = (dimensaoCelula-300) / jogo.getTamanho();
        for (int linha = 0; linha < jogo.getTamanho(); linha++) {
            for (int coluna = 0; coluna < jogo.getTamanho(); coluna++) {
                Button celula = new Button(this);
                celula.setLayoutParams(new GridLayout.LayoutParams());
                celula.getLayoutParams().width = tamanhoCelula;
                celula.getLayoutParams().height = tamanhoCelula;
                celula.setPadding(0, 0, 0, 0);

                // Define a cor de fundo (estilo xadrez)
                if ((linha + coluna) % 2 == 0) {
                    celula.setBackgroundColor(Color.parseColor("#FFF8E1"));
                } else {
                    celula.setBackgroundColor(Color.parseColor("#FFE082"));
                }

                // Verifica se tem uma rainha e se ela está em conflito
                if (jogo.possuiRainha(linha, coluna)) {
                    celula.setText("♛");
                    celula.setTextSize(24);
                    if (jogo.temConflito(linha, coluna)) {
                        celula.setTextColor(Color.RED);
                        celula.setBackgroundColor(Color.parseColor("#FFCDD2")); // Fundo vermelho claro
                    } else {
                        celula.setTextColor(Color.parseColor("#6D28D9")); // Roxo
                    }
                }

                final int finalLinha = linha;
                final int finalColuna = coluna;
                celula.setOnClickListener(v -> {
                    jogo.alternarRainha(finalLinha, finalColuna);
                    desenharTabuleiro();
                });

                gradeTabuleiro.addView(celula);
            }
        }
        atualizarMensagem();
    }

    private void atualizarMensagem() {
        if (jogo.estaResolvido()) {
            textoMensagem.setText("🎉 Parabéns! Você resolveu o puzzle!");
            textoMensagem.setTextColor(Color.parseColor("#388E3C")); // Verde
        } else if (jogo.temConflitos()) {
            textoMensagem.setText("Conflitos detectados!");
            textoMensagem.setTextColor(Color.RED);
        } else {
            textoMensagem.setText("Coloque/remova rainhas tocando nas casas.");
            textoMensagem.setTextColor(Color.DKGRAY);
        }
    }


    @Override
    protected void onResume() {
        SharedPreferences prefs = getSharedPreferences("config_nrainhas", MODE_PRIVATE);
        boolean musicaAtiva = prefs.getBoolean("musica_ativa", true);
        int tamanho = prefs.getInt("tamanho_tabuleiro", 4);

        if (jogo.getTamanho() != tamanho) {
            jogo.reiniciar(tamanho);
            desenharTabuleiro();
        }

        Intent it = new Intent(MainActivity.this, AudioService.class);

        if (musicaAtiva){
            it.setAction("PLAY");
            startService(it);
        } else {
            it.setAction("PAUSE");
            startService(it);
        }

        super.onResume();
    }

    @Override
    protected void onPause() {
        Intent it = new Intent(MainActivity.this, AudioService.class);
        it.setAction("PAUSE");
        startService(it);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent it = new Intent(MainActivity.this, AudioService.class);
        it.setAction("STOP");
        startService(it);
        super.onDestroy();
    }



}