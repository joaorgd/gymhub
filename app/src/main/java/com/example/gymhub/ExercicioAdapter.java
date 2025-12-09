package com.example.gymhub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gymhub.database.Exercicio;
import java.util.ArrayList;
import java.util.List;

public class ExercicioAdapter extends RecyclerView.Adapter<ExercicioAdapter.ExercicioViewHolder> {

    private List<Exercicio> listaExercicios = new ArrayList<>();

    public void setListaExercicios(List<Exercicio> novosExercicios) {
        this.listaExercicios = novosExercicios;
        notifyDataSetChanged(); // Atualiza a lista na tela
    }

    @NonNull
    @Override
    public ExercicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exercicio, parent, false);
        return new ExercicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExercicioViewHolder holder, int position) {
        Exercicio exercicio = listaExercicios.get(position);
        holder.tvNome.setText(exercicio.nome);
        holder.tvGrupo.setText(exercicio.grupoMuscular.toUpperCase());

        if (exercicio.observacoes != null && !exercicio.observacoes.isEmpty()) {
            holder.tvObs.setText(exercicio.observacoes);
            holder.tvObs.setVisibility(View.VISIBLE);
        } else {
            holder.tvObs.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(view -> {
            android.content.Context context = view.getContext();
            android.content.Intent intent = new android.content.Intent(context, DetalhesExercicioActivity.class);

            // Passamos os dados para a próxima tela
            intent.putExtra("EXTRA_ID", exercicio.id);
            intent.putExtra("EXTRA_NOME", exercicio.nome);
            intent.putExtra("EXTRA_GRUPO", exercicio.grupoMuscular);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaExercicios.size();
    }

    static class ExercicioViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome, tvGrupo, tvObs;

        public ExercicioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNomeExercicio);
            tvGrupo = itemView.findViewById(R.id.tvGrupoMuscular);
            tvObs = itemView.findViewById(R.id.tvObservacoes);
        }
    }
}