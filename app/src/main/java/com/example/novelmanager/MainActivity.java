package com.example.novelmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novelmanager.model.Novel;
import com.example.novelmanager.ui.NovelAdapter;
import com.example.novelmanager.viewmodel.NovelViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextAuthor, editTextGenre, editTextYear;
    private RecyclerView recyclerView;
    private NovelAdapter novelAdapter;
    private NovelViewModel novelViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de los elementos de la UI
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        editTextGenre = findViewById(R.id.editTextGenre);
        editTextYear = findViewById(R.id.editTextYear);
        Button buttonSaveNovel = findViewById(R.id.buttonSaveNovel);
        Button buttonShowMap = findViewById(R.id.buttonShowMap);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Configuración del adaptador
        novelAdapter = new NovelAdapter(new ArrayList<>(), novel ->
                Toast.makeText(MainActivity.this, "Seleccionaste: " + novel.getTitle(), Toast.LENGTH_SHORT).show());
        recyclerView.setAdapter(novelAdapter);

        // Configuración del ViewModel
        novelViewModel = new ViewModelProvider(this).get(NovelViewModel.class);
        novelViewModel.getAllNovels().observe(this, new Observer<List<Novel>>() {
            @Override
            public void onChanged(List<Novel> novels) {
                novelAdapter.setNovels(novels);
            }
        });

        // Botón para guardar una novela
        buttonSaveNovel.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString();
            String author = editTextAuthor.getText().toString();
            String genre = editTextGenre.getText().toString();
            String yearStr = editTextYear.getText().toString();

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(genre) || TextUtils.isEmpty(yearStr)) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int year = Integer.parseInt(yearStr);
            Novel novel = new Novel(title, author, genre, year, 0.0, 0.0); // Latitud y longitud en 0 por defecto
            novelViewModel.insert(novel);
            Toast.makeText(this, "Novela guardada", Toast.LENGTH_SHORT).show();

            // Limpiar campos
            editTextTitle.setText("");
            editTextAuthor.setText("");
            editTextGenre.setText("");
            editTextYear.setText("");
        });

        // Botón para abrir el mapa
        buttonShowMap.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
        });
    }
}
