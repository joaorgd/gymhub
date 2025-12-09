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

// Imports do Gráfico
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import com.example.gymhub.database.AppDatabase;
import com.example.gymhub.database.HistoricoCarga;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetalhesExercicioActivity extends AppCompatActivity {

    private int exercicioId;
    private TextView tvNome, tvGrupo;
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_exercicio);

        String nome = getIntent().getStringExtra("EXTRA_NOME");
        String grupo = getIntent().getStringExtra("EXTRA_GRUPO");
        exercicioId = getIntent().getIntExtra("EXTRA_ID", -1);

        tvNome = findViewById(R.id.tvNomeDetalhe);
        tvGrupo = findViewById(R.id.tvGrupoDetalhe);
        ImageButton btnVoltar = findViewById(R.id.btnVoltar);
        Button btnRegistrar = findViewById(R.id.btnRegistrarTreino);
        lineChart = findViewById(R.id.lineChart);

        tvNome.setText(nome);
        tvGrupo.setText(grupo);

        btnVoltar.setOnClickListener(v -> finish());
        btnRegistrar.setOnClickListener(v -> mostrarDialogoRegistro());

        atualizarGrafico(); // Carrega o gráfico ao abrir
    }

    private void atualizarGrafico() {
        List<HistoricoCarga> historico = AppDatabase.getDatabase(this)
                .treinoDao()
                .buscarHistoricoPorExercicio(exercicioId);

        if (historico == null || historico.isEmpty()) {
            lineChart.clear();
            lineChart.setNoDataText("Sem histórico. Registre seu primeiro treino!");
            lineChart.setNoDataTextColor(Color.WHITE);
            return;
        }

        List<Entry> entradas = new ArrayList<>();
        List<String> datas = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.getDefault());

        for (int i = 0; i < historico.size(); i++) {
            HistoricoCarga item = historico.get(i);
            entradas.add(new Entry(i, (float) item.carga));
            datas.add(sdf.format(new Date(item.dataTreino)));
        }

        LineDataSet dataSet = new LineDataSet(entradas, "Evolução (kg)");
        int corRosa = Color.parseColor("#FF006E"); // Nossa cor Premium

        dataSet.setColor(corRosa);
        dataSet.setCircleColor(corRosa);
        dataSet.setLineWidth(3f);
        dataSet.setCircleRadius(5f);
        dataSet.setDrawCircleHole(false);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(12f);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(corRosa);
        dataSet.setFillAlpha(50);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        // Estilização do Gráfico
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setTextColor(Color.WHITE);
        lineChart.getXAxis().setTextColor(Color.WHITE);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(datas));
        lineChart.getAxisLeft().setTextColor(Color.WHITE);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.animateY(1000);
        lineChart.invalidate();
    }

    private void mostrarDialogoRegistro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_registrar_carga, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextInputEditText editCarga = view.findViewById(R.id.editCarga);
        TextInputEditText editReps = view.findViewById(R.id.editReps);
        Button btnSalvar = view.findViewById(R.id.btnSalvarDialog);
        Button btnCancelar = view.findViewById(R.id.btnCancelarDialog);

        btnCancelar.setOnClickListener(v -> dialog.dismiss());

        btnSalvar.setOnClickListener(v -> {
            String cargaStr = editCarga.getText().toString();
            String repsStr = editReps.getText().toString();

            if (cargaStr.isEmpty() || repsStr.isEmpty()) return;

            double carga = Double.parseDouble(cargaStr);
            int reps = Integer.parseInt(repsStr);

            salvarTreinoEAnalizar(carga, reps);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void salvarTreinoEAnalizar(double carga, int reps) {
        HistoricoCarga novo = new HistoricoCarga(exercicioId, System.currentTimeMillis(), carga, reps);
        AppDatabase.getDatabase(this).treinoDao().inserirHistorico(novo);

        analisarProgresso(carga, reps);
        atualizarGrafico(); // Atualiza o visual na hora!
    }

    private void analisarProgresso(double carga, int reps) {
        String msg;
        if (reps > 12) {
            msg = String.format("🚀 Leve! Tenta %.1fkg no próximo.", carga * 1.05);
        } else if (reps < 6) {
            msg = "⚠️ Pesado! Foca na técnica.";
        } else {
            msg = "✅ Ótimo! Mantém a carga.";
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}