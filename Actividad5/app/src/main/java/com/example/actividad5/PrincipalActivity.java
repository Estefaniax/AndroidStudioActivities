package com.example.actividad5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class PrincipalActivity extends AppCompatActivity {

    private EditText editTextNombre, editTextEdad;
    private RadioGroup radioGroupSexo;
    private RadioButton radioHombre, radioMujer;
    private Button buttonCalorias, buttonBoston;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextEdad = findViewById(R.id.editTextEdad);
        radioGroupSexo = findViewById(R.id.radioGroupSexo);
        buttonCalorias = findViewById(R.id.buttonCalorias);
        buttonBoston = findViewById(R.id.buttonBoston);

        buttonCalorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalActivity.this, CaloriasActivity.class);
                // Enviar datos a la siguiente Activity
                intent.putExtra("nombre", editTextNombre.getText().toString());
                intent.putExtra("edad", editTextEdad.getText().toString());
                intent.putExtra("sexo", getSexoSeleccionado());
                startActivity(intent);
            }
        });

        buttonBoston.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalActivity.this, BostonActivity.class);
                // Enviar datos a la siguiente Activity
                intent.putExtra("nombre", editTextNombre.getText().toString());
                intent.putExtra("edad", Integer.parseInt(editTextEdad.getText().toString()));
                intent.putExtra("sexo", getSexoSeleccionado());
                startActivity(intent);

            }
        });
    }

    private String getSexoSeleccionado() {
        int selectedId = radioGroupSexo.getCheckedRadioButtonId();
        if (selectedId == R.id.radioHombre) {
            return "Hombre";
        } else if (selectedId == R.id.radioMujer) {
            return "Mujer";
        }
        return "";
    }
}
