package com.example.convertidordedatos;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText valorInput;
    private Spinner spinnerOrigen, spinnerDestino;
    private Button btnConvertir;
    private TextView resultadoTextView;

    private String[] unidades = {
            "bit", "Byte", "Kilobyte", "Kibibyte", "Megabyte", "Mebibyte",
            "Gigabyte", "Gibibyte", "Terabyte", "Tebibyte", "Petabyte", "Pebibyte"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valorInput = findViewById(R.id.Valor);
        spinnerOrigen = findViewById(R.id.Origen);
        spinnerDestino = findViewById(R.id.Destino);
        btnConvertir = findViewById(R.id.btnConvertir);
        resultadoTextView = findViewById(R.id.Resultado);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, unidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerOrigen.setAdapter(adapter);
        spinnerDestino.setAdapter(adapter);

        btnConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarConversion();
            }
        });
    }

    private void realizarConversion() {
        String valorStr = valorInput.getText().toString();
        if (valorStr.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese un valor", Toast.LENGTH_SHORT).show();
            return;
        }

        double valor = Double.parseDouble(valorStr);

        String unidadOrigen = spinnerOrigen.getSelectedItem().toString();
        String unidadDestino = spinnerDestino.getSelectedItem().toString();

        double resultado = convertir(valor, unidadOrigen, unidadDestino);

        resultadoTextView.setText(String.valueOf(resultado));
    }

    private double convertir(double valor, String unidadOrigen, String unidadDestino) {
        double valorEnBytes = convertirABytes(valor, unidadOrigen);
        return convertirDesdeBytes(valorEnBytes, unidadDestino);
    }

    private double convertirABytes(double valor, String unidad) {
        switch (unidad) {
            case "bit":
                return valor / 8;
            case "Byte":
                return valor;
            case "Kilobyte":
                return valor * 1000;
            case "Kibibyte":
                return valor * 1024;
            case "Megabyte":
                return valor * 1000000;
            case "Mebibyte":
                return valor * 1048576;
            case "Gigabyte":
                return valor * 1000000000;
            case "Gibibyte":
                return valor * 1073741824;
            case "Terabyte":
                return valor * 1000000000000L;
            case "Tebibyte":
                return valor * 1099511627776L;
            case "Petabyte":
                return valor * 1000000000000000L;
            case "Pebibyte":
                return valor * 1125899906842624L;
            default:
                return valor;
        }
    }

    private double convertirDesdeBytes(double valorEnBytes, String unidad) {
        switch (unidad) {
            case "bit":
                return valorEnBytes * 8;
            case "Byte":
                return valorEnBytes;
            case "Kilobyte":
                return valorEnBytes / 1000;
            case "Kibibyte":
                return valorEnBytes / 1024;
            case "Megabyte":
                return valorEnBytes / 1000000;
            case "Mebibyte":
                return valorEnBytes / 1048576;
            case "Gigabyte":
                return valorEnBytes / 1000000000;
            case "Gibibyte":
                return valorEnBytes / 1073741824;
            case "Terabyte":
                return valorEnBytes / 1000000000000L;
            case "Tebibyte":
                return valorEnBytes / 1099511627776L;
            case "Petabyte":
                return valorEnBytes / 1000000000000000L;
            case "Pebibyte":
                return valorEnBytes / 1125899906842624L;
            default:
                return valorEnBytes;
        }
    }
}
