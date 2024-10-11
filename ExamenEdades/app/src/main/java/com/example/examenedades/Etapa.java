package com.example.examenedades;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Etapa extends AppCompatActivity {

    private TextView vNombre, vFecha, vEdad, vSexo, vEtapa;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etapa);

        vNombre = findViewById(R.id.VNombre);
        vFecha = findViewById(R.id.VFecha);
        vEdad = findViewById(R.id.VEdad);
        vSexo = findViewById(R.id.VSex);
        vEtapa = findViewById(R.id.VEtapa);
        btnVolver = findViewById(R.id.BVolver);

        // Obtener los datos del Intent
        String nombre = getIntent().getStringExtra("nombre");
        int dia = getIntent().getIntExtra("dia", 0);
        int mes = getIntent().getIntExtra("mes", 0);
        int anio = getIntent().getIntExtra("anio", 0);
        int edad = getIntent().getIntExtra("edad", 0);
        String sexo = getIntent().getStringExtra("sexo");

        // Mostrar los datos
        vNombre.setText(nombre);
        vFecha.setText(dia + " de " + obtenerNombreMes(mes) + " del año " + anio);
        vEdad.setText("Edad: " + edad + " años");
        vSexo.setText(sexo);
        vEtapa.setText(obtenerEtapa(edad));

        // Botón para volver a la actividad principal
        btnVolver.setOnClickListener(view -> finish());
    }

    // Método para obtener el nombre del mes
    private String obtenerNombreMes(int mes) {
        String[] meses = {"", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        return meses[mes];
    }

    // Método para determinar la etapa de vida
    private String obtenerEtapa(int edad) {
        if (edad >= 0 && edad <= 2) return "Maternal";
        if (edad >= 3 && edad <= 5) return "Kinder";
        if (edad >= 6 && edad <= 12) return "Primaria";
        if (edad >= 13 && edad <= 15) return "Secundaria";
        if (edad >= 16 && edad <= 18) return "Prepa";
        if (edad >= 19 && edad <= 24) return "Universidad";
        if (edad >= 25 && edad <= 65) return "Trabaja";
        if (edad > 65) return "Jubilado/a";
        return "Indeterminado";
    }
}
