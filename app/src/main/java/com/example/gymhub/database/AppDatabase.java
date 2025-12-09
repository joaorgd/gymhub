package com.example.gymhub.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Exercicio.class, HistoricoCarga.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TreinoDao treinoDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "gympass_pessoal_db")
                            .allowMainThreadQueries() // Permitido para simplificar projetos acadêmicos
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}