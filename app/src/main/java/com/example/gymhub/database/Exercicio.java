package com.example.gymhub.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tabela_exercicios")
public class Exercicio {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nome;           // Ex: "Supino Reto"
    public String grupoMuscular;  // Ex: "Peito"
    public String observacoes;    // Ex: "Banco inclinado"

    // Construtor vazio (obrigatório para o Room)
    public Exercicio() {}

    // Construtor para facilitar nossa vida
    public Exercicio(String nome, String grupoMuscular, String observacoes) {
        this.nome = nome;
        this.grupoMuscular = grupoMuscular;
        this.observacoes = observacoes;
    }
}