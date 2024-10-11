package com.example.actividadexamenes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNombre;
    private RadioGroup radioGroupExamen;
    private Button btnContestar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNombre = findViewById(R.id.nombreInput);
        radioGroupExamen = findViewById(R.id.subjectRadioGroup);
        btnContestar = findViewById(R.id.submitButton);

        btnContestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = editTextNombre.getText().toString();
                int selectedExamen = radioGroupExamen.getCheckedRadioButtonId();

                if (selectedExamen == R.id.radioMate) {
                    Intent intent = new Intent(MainActivity.this, ActivityMate.class);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("examen", 1);
                    startActivity(intent);
                } else if (selectedExamen == R.id.radioEspanol) {
                    Intent intent = new Intent(MainActivity.this, ActivityEspa.class);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("examen", 2);
                    startActivity(intent);
                }
            }
        });
    }
}
