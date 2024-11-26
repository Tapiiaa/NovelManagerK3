package com.example.novelmanager;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.novelmanager.database.NovelDatabaseHelper;
import com.example.novelmanager.model.Novel;

public class MainActivity extends AppCompatActivity {

    private EditText titleEditText, authorEditText, genreEditText, yearEditText;
    private Button addNovelButton, showNovelsButton;
    private NovelDatabaseHelper databaseHelper;

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

        databaseHelper = new NovelDatabaseHelper(this);

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

            if (title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Novel novel = new Novel(title, author, genre, year);
            databaseHelper.addNovel(novel);
            Toast.makeText(this, "Novela agregada con éxito", Toast.LENGTH_SHORT).show();
            clearFields();
        });

        showNovelsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ShowNovelsActivity.class);
            startActivity(intent);
        });
    }

    private void clearFields() {
        titleEditText.setText("");
        authorEditText.setText("");
        genreEditText.setText("");
        yearEditText.setText("");
    }
}


