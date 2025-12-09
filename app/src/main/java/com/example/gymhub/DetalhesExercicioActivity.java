package com.example.gymhub;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gymhub.database.AppDatabase;
import com.example.gymhub.database.HistoricoCarga;
import com.google.android.material.textfield.TextInputEditText;

public class DetalhesExercicioActivity extends AppCompatActivity {

    private int exercicioId;
    private TextView tvNome, tvGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_exercicio);

        // 1. Recuperar dados
        String nome = getIntent().getStringExtra("EXTRA_NOME");
        String grupo = getIntent().getStringExtra("EXTRA_GRUPO");
        exercicioId = getIntent().getIntExtra("EXTRA_ID", -1);

        // 2. Vincular elementos
        tvNome = findViewById(R.id.tvNomeDetalhe);
        tvGrupo = findViewById(R.id.tvGrupoDetalhe);
        ImageButton btnVoltar = findViewById(R.id.btnVoltar);
        Button btnRegistrar = findViewById(R.id.btnRegistrarTreino);

        tvNome.setText(nome);
        tvGrupo.setText(grupo);

        btnVoltar.setOnClickListener(v -> finish());

        // 3. Ação Principal: Abrir o Diálogo
        btnRegistrar.setOnClickListener(v -> mostrarDialogoRegistro());
    }

    private void mostrarDialogoRegistro() {
        // Inflar o layout customizado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_registrar_carga, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        // Deixa o fundo do dialog transparente para respeitar os cantos arredondados do CardView
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // Vincular elementos do Dialog
        TextInputEditText editCarga = view.findViewById(R.id.editCarga);
        TextInputEditText editReps = view.findViewById(R.id.editReps);
        Button btnSalvar = view.findViewById(R.id.btnSalvarDialog);
        Button btnCancelar = view.findViewById(R.id.btnCancelarDialog);

        btnCancelar.setOnClickListener(v -> dialog.dismiss());

        btnSalvar.setOnClickListener(v -> {
            String cargaStr = editCarga.getText().toString();
            String repsStr = editReps.getText().toString();

            if (cargaStr.isEmpty() || repsStr.isEmpty()) {
                Toast.makeText(this, "Preenche todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            double carga = Double.parseDouble(cargaStr);
            int reps = Integer.parseInt(repsStr);

            salvarTreinoEAnalizar(carga, reps);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void salvarTreinoEAnalizar(double carga, int reps) {
        // 1. Salvar no Banco (Room)
        HistoricoCarga novoHistorico = new HistoricoCarga(
                exercicioId,
                System.currentTimeMillis(),
                carga,
                reps
        );
        AppDatabase.getDatabase(this).treinoDao().inserirHistorico(novoHistorico);

        // 2. Algoritmo de Progressive Overload (Sobrecarga Progressiva)
        analisarProgresso(carga, reps);
    }

    private void analisarProgresso(double cargaAtual, int repsAtuais) {
        String feedback;

        if (repsAtuais > 12) {
            // Se fez mais de 12 reps, está leve. Sugerir aumento de 5% a 10%
            double novaCarga = cargaAtual * 1.05; // +5%
            feedback = String.format("🚀 Ficou leve! Aumenta a carga para %.1f kg no próximo treino.", novaCarga);
        } else if (repsAtuais < 6) {
            // Se fez menos de 6, está pesado demais para hipertrofia padrão
            feedback = "⚠️ Carga alta! Tenta diminuir um pouco para manter a técnica.";
        } else {
            // Entre 6 e 12 (Zona de Hipertrofia)
            feedback = "✅ Ótimo treino! Mantém essa carga e foca na execução.";
        }

        // Exibir o feedback para o utilizador
        Toast.makeText(this, feedback, Toast.LENGTH_LONG).show();

        // (Opcional) Aqui poderias atualizar um TextView na tela também!
    }
}