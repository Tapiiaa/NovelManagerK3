package com.example.novelmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText nameInput;
    private Button saveNameButton;
    private Button proceedButton;
    private TextView welcomeText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameInput = findViewById(R.id.name_input);
        saveNameButton = findViewById(R.id.save_name_button);
        proceedButton = findViewById(R.id.proceed_button);
        welcomeText = findViewById(R.id.welcome_text);

        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        // Configurar botón para guardar el nombre
        saveNameButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            if (!name.isEmpty()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userName", name);
                editor.apply();

                // Actualizar el texto de bienvenida
                welcomeText.setText("Bienvenido, " + name);
            }
        });

        // Configurar botón para proceder a la MainActivity
        proceedButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Mostrar el nombre guardado en el texto de bienvenida si ya existe
        String name = sharedPreferences.getString("userName", "");
        if (!name.isEmpty()) {
            welcomeText.setText("Bienvenido, " + name);
        }
    }
}
