package com.example.novelmanager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

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

        addNovelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String author = authorEditText.getText().toString();
                String genre = genreEditText.getText().toString();
                int year = Integer.parseInt(yearEditText.getText().toString());
                Novel novel = new Novel(title, author, genre, year);
                databaseHelper.addNovel(novel, true);
            }
        });

        showNovelsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowNovelsActivity.class);
                startActivity(intent);
            }
        });
    }
}
