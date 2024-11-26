package com.example.novelmanager;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novelmanager.database.NovelDatabaseHelper;
import com.example.novelmanager.model.Novel;
import com.example.novelmanager.ui.NovelAdapter;

import java.util.List;

public class ShowNovelsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NovelAdapter adapter;
    private NovelDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_novels);

        recyclerView = findViewById(R.id.recyclerViewNovels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new NovelDatabaseHelper(this);

        // Obtén la lista de novelas desde la base de datos
        List<Novel> novels = databaseHelper.getAllNovels();

        // Configura el adaptador con la lista y un listener para manejar clics
        adapter = new NovelAdapter(novels, novel -> {
            // Limpia y analiza el género
            String genre = novel.getGenre().trim().toLowerCase();

            // Mostrar mensaje basado en el género
            String message;
            switch (genre) {
                case "ficción":
                    message = "Un género lleno de creatividad: Ficción.";
                    break;
                case "terror":
                    message = "¡Prepárate para pasar miedo con este libro de Terror!";
                    break;
                case "romance":
                    message = "Historias llenas de amor: Romance.";
                    break;
                case "fantasía":
                    message = "Explora mundos mágicos con este libro de Fantasía.";
                    break;
                case "aventura":
                    message = "Grandes aventuras te esperan.";
                    break;
                default:
                    message = "Este es un género interesante: " + novel.getGenre();
            }

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(adapter);
    }
}


