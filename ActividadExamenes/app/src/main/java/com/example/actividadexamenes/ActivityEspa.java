package com.example.actividadexamenes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityEspa extends AppCompatActivity {

    private RadioGroup p1, p2, p3, p4;
    private Button btnCalificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espa);

        p1 = findViewById(R.id.groupPregunta1Espanol);
        p2 = findViewById(R.id.groupPregunta2Espanol);
        p3 = findViewById(R.id.groupPregunta3Espanol);
        p4 = findViewById(R.id.groupPregunta4Espanol);
        btnCalificar = findViewById(R.id.btnCalificarEspanol);

        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        int examen = intent.getIntExtra("examen", 1);

        btnCalificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int respP1 = p1.getCheckedRadioButtonId();
                int respP2 = p2.getCheckedRadioButtonId();
                int respP3 = p3.getCheckedRadioButtonId();
                int respP4 = p4.getCheckedRadioButtonId();

                Intent intent = new Intent(ActivityEspa.this, ActivityCalificar.class);
                intent.putExtra("nombre", nombre);
                intent.putExtra("examen", examen);
                intent.putExtra("p1", respP1);
                intent.putExtra("p2", respP2);
                intent.putExtra("p3", respP3);
                intent.putExtra("p4", respP4);
                startActivity(intent);
            }
        });
    }
}
