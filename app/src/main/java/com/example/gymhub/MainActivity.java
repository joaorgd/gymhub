package com.example.gymhub;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.gymhub.database.AppDatabase;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExercicioAdapter adapter;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewExercicios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExercicioAdapter();
        recyclerView.setAdapter(adapter);

        // 2. Configurar Botão de Adicionar (FAB)
        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CadastroExercicioActivity.class);
            startActivity(intent);
        });

        // 3. Observar dados do Banco de Dados (LiveData)
        // Isso atualiza a lista automaticamente sempre que você adicionar/editar algo
        AppDatabase.getDatabase(this)
                .treinoDao()
                .listarTodosExercicios()
                .observe(this, listaExercicios -> {
                    adapter.setListaExercicios(listaExercicios);
                });
    }
}