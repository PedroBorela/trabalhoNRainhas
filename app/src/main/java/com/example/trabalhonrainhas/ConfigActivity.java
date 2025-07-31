package com.example.trabalhonrainhas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConfigActivity extends AppCompatActivity {

    Button botaoVoltar;

    Button botaoSalvar;
    int tamanhoSelecionado;
    private Spinner seletorTamanho;

    private  boolean musicaAtiva;
    private SwitchCompat switchMusica;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_config);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        seletorTamanho = findViewById(R.id.seletorTamanho);
        botaoVoltar = findViewById(R.id.botaoVoltar);
        botaoSalvar = findViewById(R.id.botaoSalvar);
        switchMusica = findViewById(R.id.switchMusica);

        SharedPreferences prefs = getSharedPreferences("config_nrainhas", MODE_PRIVATE);

        tamanhoSelecionado = prefs.getInt("tamanho_tabuleiro", 4);

         musicaAtiva = prefs.getBoolean("musica_ativa", true);
        switchMusica.setChecked(musicaAtiva);
        switchMusica.setOnCheckedChangeListener((buttonView, isChecked) ->
                musicaAtiva = isChecked);

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tamanhos_tabuleiro));
        seletorTamanho.setAdapter(adaptador);
        if(tamanhoSelecionado >= 4){
            seletorTamanho.setSelection(tamanhoSelecionado-4);
        }
        seletorTamanho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                tamanhoSelecionado = 4+pos;



            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });



        botaoVoltar.setOnClickListener(v ->{
            finish();

        });

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("config_nrainhas", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("tamanho_tabuleiro", tamanhoSelecionado);
                editor.putBoolean("musica_ativa", musicaAtiva);
                editor.apply();
                finish();
                Toast.makeText(ConfigActivity.this, "Configurações salvas", Toast.LENGTH_SHORT).show();
         }
});


    }
}