package com.example.gymhub;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetalhesExercicioActivity extends AppCompatActivity {

    private int exercicioId; // Vamos usar isso para salvar o histórico depois

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_exercicio);

        // 1. Recuperar dados vindos da Intent (da tela anterior)
        String nome = getIntent().getStringExtra("EXTRA_NOME");
        String grupo = getIntent().getStringExtra("EXTRA_GRUPO");
        exercicioId = getIntent().getIntExtra("EXTRA_ID", -1);

        // 2. Vincular com a tela
        TextView tvNome = findViewById(R.id.tvNomeDetalhe);
        TextView tvGrupo = findViewById(R.id.tvGrupoDetalhe);
        ImageButton btnVoltar = findViewById(R.id.btnVoltar);
        Button btnRegistrar = findViewById(R.id.btnRegistrarTreino);

        tvNome.setText(nome);
        tvGrupo.setText(grupo);

        // 3. Ações dos botões
        btnVoltar.setOnClickListener(v -> finish()); // Volta para a lista

        btnRegistrar.setOnClickListener(v -> {
            // Aqui abriremos o dialog para inserir peso/reps (Próximo passo)
            Toast.makeText(this, "Funcionalidade de Registro em breve!", Toast.LENGTH_SHORT).show();
        });
    }
}