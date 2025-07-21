package com.example.trabalhonrainhas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
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
    private Spinner seletorTamanho;
    private Button botaoReiniciar;
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

        //Inicia a música
        Intent it = new Intent(MainActivity.this, AudioService.class);
        it.setAction("PLAY");
        startService(it);






        jogo = new TabuleiroRainhas(4);

        gradeTabuleiro = findViewById(R.id.boardGrid);
        textoMensagem = findViewById(R.id.messageText);
        seletorTamanho = findViewById(R.id.boardSizeSpinner);
        botaoReiniciar = findViewById(R.id.resetButton);

        seletorTamanho.setSelection(0); // Posição para "4x4"
        seletorTamanho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                jogo.reiniciar(4 + pos);
                desenharTabuleiro();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        botaoReiniciar.setOnClickListener(v -> {
            jogo.reiniciar(jogo.getTamanho());
            desenharTabuleiro();
        });

        desenharTabuleiro();
    }

    private void desenharTabuleiro() {
        gradeTabuleiro.removeAllViews();
        gradeTabuleiro.setColumnCount(jogo.getTamanho());
        gradeTabuleiro.setRowCount(jogo.getTamanho());

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int cellSize = screenWidth / jogo.getTamanho();

        for (int linha = 0; linha < jogo.getTamanho(); linha++) {
            for (int coluna = 0; coluna < jogo.getTamanho(); coluna++) {
                Button celula = new Button(this);
                celula.setLayoutParams(new GridLayout.LayoutParams());
                celula.getLayoutParams().width = cellSize;
                celula.getLayoutParams().height = cellSize;
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
        Intent it = new Intent(MainActivity.this, AudioService.class);
        it.setAction("PLAY");
        startService(it);
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