package com.example.gymfy;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.example.gymfy.database.AppDatabase;
import com.example.gymfy.database.Exercicio;

public class CadastroExercicioActivity extends AppCompatActivity {

    private TextInputEditText editNome, editGrupo, editObs;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_exercicio);

        // 1. Vincular os componentes da tela
        editNome = findViewById(R.id.editNome);
        editGrupo = findViewById(R.id.editGrupo);
        editObs = findViewById(R.id.editObs);
        btnSalvar = findViewById(R.id.btnSalvar);

        // 2. Ação do Botão Salvar
        btnSalvar.setOnClickListener(v -> salvarExercicio());
    }

    private void salvarExercicio() {
        String nome = editNome.getText().toString().trim();
        String grupo = editGrupo.getText().toString().trim();
        String obs = editObs.getText().toString().trim();

        // Validação simples
        if (nome.isEmpty()) {
            editNome.setError("O nome é obrigatório");
            return;
        }
        if (grupo.isEmpty()) {
            editGrupo.setError("O grupo muscular é obrigatório");
            return;
        }

        // 3. Criar o objeto e salvar no Banco
        Exercicio novoExercicio = new Exercicio(nome, grupo, obs);

        try {
            AppDatabase.getDatabase(this).treinoDao().inserirExercicio(novoExercicio);

            Toast.makeText(this, "Exercício salvo com sucesso!", Toast.LENGTH_SHORT).show();
            finish(); // Fecha a tela e volta para a anterior
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao salvar: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}