package com.example.actividad5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CaloriasActivity extends AppCompatActivity {

    private TextView textViewNombre, textViewEdad, textViewSexo, textViewResultado;
    private EditText editTextPeso, editTextEstatura;
    private Button BTNCalcular, BTNSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorias);


        textViewNombre = findViewById(R.id.textViewNombre);
        textViewEdad = findViewById(R.id.textViewEdad);
        textViewSexo = findViewById(R.id.textViewSexo);
        textViewResultado = findViewById(R.id.textViewResultado);
        editTextPeso = findViewById(R.id.editTextPeso);
        editTextEstatura = findViewById(R.id.editTextEstatura);
        BTNCalcular = findViewById(R.id.BTNCalcular);
        BTNSalir = findViewById(R.id.BTNSalir);

        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        String edad = intent.getStringExtra("edad");
        String sexo = intent.getStringExtra("sexo");

        textViewNombre.setText("Nombre: " + nombre);
        textViewEdad.setText("Edad: " + edad);
        textViewSexo.setText("Sexo: " + sexo);

        // Botón para calcular calorías
        BTNCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double peso = Double.parseDouble(editTextPeso.getText().toString());
                double estatura = Double.parseDouble(editTextEstatura.getText().toString());

                double caloriasBasales = calcularCalorias(peso, estatura, Integer.parseInt(edad), sexo);
                textViewResultado.setText("Calorías Basales: " + caloriasBasales);
            }
        });

        // Botón para salir
        BTNSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaloriasActivity.this, PrincipalActivity.class);
                startActivity(intent);
            }
        });
    }

    private double calcularCalorias(double peso, double estatura, int edad, String sexo) {
        if (sexo.equals("Hombre")) {
            // TMB para hombres
            return (10 * peso) + (6.25 * estatura) - (5 * edad) + 5;
        } else {
            // TMB para mujeres
            return (10 * peso) + (6.25 * estatura) - (5 * edad) - 161;
        }
    }

}
