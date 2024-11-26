package com.example.novelmanager;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.novelmanager.database.NovelDatabaseHelper;
import com.example.novelmanager.model.Novel;
import com.example.novelmanager.workers.NovelSyncWorker;

public class MainActivity extends AppCompatActivity {

    private EditText titleEditText, authorEditText, genreEditText, yearEditText, imagePathEditText;
    private Button addNovelButton, showNovelsButton, syncButton;
    private NovelDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        titleEditText = findViewById(R.id.editTextTitle);
        authorEditText = findViewById(R.id.editTextAuthor);
        genreEditText = findViewById(R.id.editTextGenre);
        yearEditText = findViewById(R.id.editTextYear);
        imagePathEditText = findViewById(R.id.editTextImagePath);
        addNovelButton = findViewById(R.id.buttonAddNovel);
        showNovelsButton = findViewById(R.id.buttonShowNovels);
        syncButton = findViewById(R.id.buttonSyncNovels);

        // Inicializar base de datos
        databaseHelper = new NovelDatabaseHelper(this);

        // Botón para agregar novela
        addNovelButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String author = authorEditText.getText().toString();
            String genre = genreEditText.getText().toString();
            int year;

            try {
                year = Integer.parseInt(yearEditText.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Por favor, ingresa un año válido", Toast.LENGTH_SHORT).show();
                return;
            }

            String imagePath = imagePathEditText.getText().toString();

            if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || imagePath.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Novel novel = new Novel(title, author, genre, year, imagePath);
            databaseHelper.addNovel(novel, true);
            Toast.makeText(this, "Novela agregada con éxito", Toast.LENGTH_SHORT).show();

            // Limpiar campos después de añadir la novela
            clearFields();
        });

        // Botón para mostrar novelas
        showNovelsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ShowNovelsActivity.class);
            startActivity(intent);
        });

        // Botón para sincronizar novelas
        syncButton.setOnClickListener(v -> scheduleNovelSync());
    }

    /**
     * Limpia los campos de entrada.
     */
    private void clearFields() {
        titleEditText.setText("");
        authorEditText.setText("");
        genreEditText.setText("");
        yearEditText.setText("");
        imagePathEditText.setText("");
    }

    /**
     * Programa la sincronización de novelas usando WorkManager.
     */
    private void scheduleNovelSync() {
        String serverUrl = "https://api.example.com/novels";

        Data inputData = new Data.Builder()
                .putString("serverUrl", serverUrl)
                .build();

        OneTimeWorkRequest syncWorkRequest = new OneTimeWorkRequest.Builder(NovelSyncWorker.class)
                .setInputData(inputData)
                .build();

        WorkManager.getInstance(this).enqueue(syncWorkRequest);
        Toast.makeText(this, "Sincronización programada", Toast.LENGTH_SHORT).show();
    }
}


