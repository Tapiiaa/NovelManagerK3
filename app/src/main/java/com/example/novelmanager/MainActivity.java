package com.example.novelmanager;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.novelmanager.database.NovelDatabaseHelper;
import com.example.novelmanager.database.NovelRepository;
import com.example.novelmanager.model.Novel;

public class MainActivity extends AppCompatActivity {

    private EditText titleEditText, authorEditText, genreEditText, yearEditText;
    private Button addNovelButton, showNovelsButton, syncButton;
    private NovelDatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleEditText = findViewById(R.id.editTextTitle);
        authorEditText = findViewById(R.id.editTextAuthor);
        genreEditText = findViewById(R.id.editTextGenre);
        yearEditText = findViewById(R.id.editTextYear);
        addNovelButton = findViewById(R.id.buttonAddNovel);
        showNovelsButton = findViewById(R.id.buttonShowNovels);
        syncButton = findViewById(R.id.buttonSyncNovels);

        databaseHelper = new NovelDatabaseHelper(this);

        addNovelButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String author = authorEditText.getText().toString();
            String genre = genreEditText.getText().toString();
            int year = Integer.parseInt(yearEditText.getText().toString());
            Novel novel = new Novel(title, author, genre, year);
            databaseHelper.addNovel(novel, true);
            Toast.makeText(this, "Novela agregada con éxito", Toast.LENGTH_SHORT).show();
        });

        showNovelsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ShowNovelsActivity.class);
            startActivity(intent);
        });

        syncButton.setOnClickListener(v -> {
            String serverUrl = "https://api.example.com/novels";
            new NovelRepository(getApplication()).fetchNovelsFromServer(serverUrl);
            Toast.makeText(this, "Sincronizando novelas...", Toast.LENGTH_SHORT).show();
        });
    }
}

