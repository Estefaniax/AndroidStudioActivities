package com.example.actividad5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BostonActivity extends AppCompatActivity {

    TextView textNombre, textEdad, textSexo, textResultado;
    Button BTNTiempoRequerido, BTNSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boston);

        textNombre = findViewById(R.id.textNombre);
        textEdad = findViewById(R.id.textEdad);
        textSexo = findViewById(R.id.textSexo);
        textResultado = findViewById(R.id.textResultado);
        BTNTiempoRequerido = findViewById(R.id.BTNTiempoRequerido);
        BTNSalir = findViewById(R.id.BTNSalir);

        String nombre = getIntent().getStringExtra("nombre");
        int edad = getIntent().getIntExtra("edad", -1);
        String sexo = getIntent().getStringExtra("sexo");

        textNombre.setText("Nombre: " + nombre);
        textEdad.setText("Edad: " + edad);
        textSexo.setText("Sexo: " + sexo);

        BTNTiempoRequerido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularTiempoRequerido(edad, sexo);
            }
        });

        BTNSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Regresar a la actividad anterior
            }
        });
    }

    private void calcularTiempoRequerido(int edad, String sexo) {
        // Definir los rangos de edad permitidos para el maratón
        if (edad < 18 || edad > 99) {
            Toast.makeText(this, "Edad no permitida para clasificar al Maratón", Toast.LENGTH_SHORT).show();
            return;
        }

        double tiempoRequerido = 0.0;

        if (sexo.equals("Hombre")) {
            tiempoRequerido = obtenerTiempoHombre(edad);
        } else {
            tiempoRequerido = obtenerTiempoMujer(edad);
        }

        textResultado.setText("Tiempo requerido: " + tiempoRequerido + " horas");
    }

    private double obtenerTiempoHombre(int edad) {
        if (edad >= 18 && edad <= 34) {
            return 3.0;  // 3:00
        } else if (edad >= 35 && edad <= 39) {
            return 3.05; // 3:05
        } else if (edad >= 40 && edad <= 44) {
            return 3.10; // 3:10
        } else if (edad >= 45 && edad <= 49) {
            return 3.20; // 3:20
        } else if (edad >= 50 && edad <= 54) {
            return 3.25; // 3:25
        } else if (edad >= 55 && edad <= 59) {
            return 3.35; // 3:35
        } else if (edad >= 60 && edad <= 64) {
            return 3.50; // 3:50
        } else if (edad >= 65 && edad <= 69) {
            return 4.05; // 4:05
        } else if (edad >= 70 && edad <= 74) {
            return 4.20; // 4:20
        } else if (edad >= 75 && edad <= 79) {
            return 4.35; // 4:35
        } else { // edad >= 80
            return 4.50; // 4:50
        }
    }

    private double obtenerTiempoMujer(int edad) {
        if (edad >= 18 && edad <= 34) {
            return 3.30; // 3:30
        } else if (edad >= 35 && edad <= 39) {
            return 3.35; // 3:35
        } else if (edad >= 40 && edad <= 44) {
            return 3.40; // 3:40
        } else if (edad >= 45 && edad <= 49) {
            return 3.50; // 3:50
        } else if (edad >= 50 && edad <= 54) {
            return 3.55; // 3:55
        } else if (edad >= 55 && edad <= 59) {
            return 4.00; // 4:00
        } else if (edad >= 60 && edad <= 64) {
            return 4.20; // 4:20
        } else if (edad >= 65 && edad <= 69) {
            return 4.35; // 4:35
        } else if (edad >= 70 && edad <= 74) {
            return 5.00; // 5:00
        } else { // edad >= 80
            return 5.20; // 5:20
        }
    }
}
