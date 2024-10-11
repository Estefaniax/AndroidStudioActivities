package com.example.actividad5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Bienvenida extends AppCompatActivity {

    TextView txt;
    String nombre, pass;
    Button buttonContinuar; // Agregamos el botón

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        txt = (TextView) findViewById(R.id.bienvenida);
        buttonContinuar = findViewById(R.id.BTNContinuar); // Enlazamos el botón

        // Recibimos parámetro de la actividad que llamó a bienvenida
        nombre = getIntent().getStringExtra("usuario");
        pass = getIntent().getStringExtra("pass");
        txt.setText("¡Bienvenid@ " + nombre + "!");

        // Configuramos el evento de clic para el botón "Continuar"
        buttonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar PrincipalActivity
                Intent intent = new Intent(Bienvenida.this, PrincipalActivity.class);
                startActivity(intent);
                finish(); // Finaliza la actividad actual para evitar que se regrese a ella
            }
        });
    }
}
