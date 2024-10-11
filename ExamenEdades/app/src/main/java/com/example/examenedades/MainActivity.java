package com.example.examenedades;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText tNombre, tDia, tMes, tAno;
    private RadioGroup radioGroupSexo;
    private RadioButton radioButtonHombre, radioButtonMujer;
    private Button btnContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tNombre = findViewById(R.id.tNombre);
        tDia = findViewById(R.id.tDia);
        tMes = findViewById(R.id.tMes);
        tAno = findViewById(R.id.TAno);
        radioGroupSexo = findViewById(R.id.BSexo);
        radioButtonHombre = findViewById(R.id.SHombre);
        radioButtonMujer = findViewById(R.id.SMujer);
        btnContinuar = findViewById(R.id.BContinuar);

        btnContinuar.setOnClickListener(view -> {
            String nombre = tNombre.getText().toString();
            int dia = Integer.parseInt(tDia.getText().toString());
            int mes = Integer.parseInt(tMes.getText().toString());
            int anio = Integer.parseInt(tAno.getText().toString());

            String sexo;
            if (radioButtonHombre.isChecked()) {
                sexo = "Hombre";
            } else if (radioButtonMujer.isChecked()) {
                sexo = "Mujer";
            } else {
                sexo = "Indeterminado";
            }

            // Calcula la edad actual
            int edad = calcularEdad(dia, mes, anio);

            // Llama a la actividad Etapa y pasa los datos
            Intent intent = new Intent(MainActivity.this, Etapa.class);
            intent.putExtra("nombre", nombre);
            intent.putExtra("dia", dia);
            intent.putExtra("mes", mes);
            intent.putExtra("anio", anio);
            intent.putExtra("edad", edad);
            intent.putExtra("sexo", sexo);
            startActivity(intent);
        });
    }

    // Método para calcular la edad
    private int calcularEdad(int dia, int mes, int anio) {
        java.util.Calendar today = java.util.Calendar.getInstance();
        int year = today.get(java.util.Calendar.YEAR);
        int currentMonth = today.get(java.util.Calendar.MONTH) + 1;
        int currentDay = today.get(java.util.Calendar.DAY_OF_MONTH);

        int edad = year - anio;

        // Ajustar si aún no ha cumplido años este año
        if (currentMonth < mes || (currentMonth == mes && currentDay < dia)) {
            edad--;
        }

        return edad;
    }
}
