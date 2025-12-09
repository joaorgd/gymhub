package com.example.gymhub.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface TreinoDao {

    // --- EXERCÍCIOS ---
    @Insert
    void inserirExercicio(Exercicio exercicio);

    @Query("SELECT * FROM tabela_exercicios ORDER BY nome ASC")
    LiveData<List<Exercicio>> listarTodosExercicios();

    @Delete
    void deletarExercicio(Exercicio exercicio);

    // --- HISTÓRICO ---
    @Insert
    void inserirHistorico(HistoricoCarga historico);

    // Busca histórico específico para gerar gráficos depois
    @Query("SELECT * FROM tabela_historico WHERE exercicioId = :exercicioId ORDER BY dataTreino ASC")
    List<HistoricoCarga> buscarHistoricoPorExercicio(int exercicioId);
}