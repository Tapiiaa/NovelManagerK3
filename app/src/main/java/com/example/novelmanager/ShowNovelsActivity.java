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
            // Lógica para manejar el clic en un elemento de la lista
            // Por ejemplo, mostrar detalles en un Toast
            Toast.makeText(this, "Seleccionaste: " + novel.getTitle(), Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(adapter);
    }
}

