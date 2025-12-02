package com.example.gymfy.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tabela_historico",
        foreignKeys = @ForeignKey(entity = Exercicio.class,
                parentColumns = "id",
                childColumns = "exercicioId",
                onDelete = ForeignKey.CASCADE)) // Se deletar o exercício, apaga o histórico junto
public class HistoricoCarga {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int exercicioId; // O elo de ligação
    public long dataTreino; // Data em milissegundos
    public double carga;    // Peso usado
    public int repeticoes;

    public HistoricoCarga() {}

    public HistoricoCarga(int exercicioId, long dataTreino, double carga, int repeticoes) {
        this.exercicioId = exercicioId;
        this.dataTreino = dataTreino;
        this.carga = carga;
        this.repeticoes = repeticoes;
    }
}