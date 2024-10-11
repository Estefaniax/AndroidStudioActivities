package com.example.actividadexamenes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityCalificar extends AppCompatActivity {

    private TextView textViewNombre, textViewMateria, textViewCalificacion, textViewEstado;
    private Button btnVolverInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar);

        textViewNombre = findViewById(R.id.textViewNombre);
        textViewMateria = findViewById(R.id.textViewMateria);
        textViewCalificacion = findViewById(R.id.textViewCalificacion);
        textViewEstado = findViewById(R.id.textViewEstado);
        btnVolverInicio = findViewById(R.id.btnVolverInicio);

        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        int examen = intent.getIntExtra("examen", 1);
        int p1 = intent.getIntExtra("p1", 0);
        int p2 = intent.getIntExtra("p2", 0);
        int p3 = intent.getIntExtra("p3", 0);
        int p4 = intent.getIntExtra("p4", 0);

        textViewNombre.setText(nombre);

        // Definir respuestas correctas para cada examen
        int[] respuestasCorrectasMate = {R.id.rbPregunta1Opcion2, R.id.rbPregunta2Opcion3, R.id.rbPregunta3Opcion2, R.id.rbPregunta4Opcion2};
        int[] respuestasCorrectasEspanol = {R.id.rbPregunta1Opcion1Espanol, R.id.rbPregunta2Opcion2Espanol, R.id.rbPregunta3Opcion2Espanol, R.id.rbPregunta4Opcion3};

        int calificacion = 0;

        if (examen == 1) {
            textViewMateria.setText("Matemáticas");
            calificacion += (p1 == respuestasCorrectasMate[0]) ? 2.5 : 0;
            calificacion += (p2 == respuestasCorrectasMate[1]) ? 2.5 : 0;
            calificacion += (p3 == respuestasCorrectasMate[2]) ? 2.5 : 0;
            calificacion += (p4 == respuestasCorrectasMate[3]) ? 2.5 : 0;
        } else {
            textViewMateria.setText("Español");
            calificacion += (p1 == respuestasCorrectasEspanol[0]) ? 2.5 : 0;
            calificacion += (p2 == respuestasCorrectasEspanol[1]) ? 2.5 : 0;
            calificacion += (p3 == respuestasCorrectasEspanol[2]) ? 2.5 : 0;
            calificacion += (p4 == respuestasCorrectasEspanol[3]) ? 2.5 : 0;
        }

        textViewCalificacion.setText(String.valueOf(calificacion));

        if (calificacion >= 6) {
            textViewEstado.setText("Aprobado");
        } else {
            textViewEstado.setText("Reprobado");
        }

        btnVolverInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCalificar.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
