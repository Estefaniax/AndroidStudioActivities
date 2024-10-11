package com.example.calculadoraactividad;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText uno, dos;
    Button sumar, restar, dividir, multiplicar, potencia, modulo, cociente, reiniciar;
    TextView resultado;
    String primero, segundo;
    double n1, n2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uno = (EditText) findViewById(R.id.numero_uno);
        dos = (EditText) findViewById(R.id.numero_dos);
        sumar = (Button) findViewById(R.id.sumar);
        restar = (Button) findViewById(R.id.restar);
        dividir = (Button) findViewById(R.id.dividir);
        multiplicar = (Button) findViewById(R.id.multiplicar);
        potencia = (Button) findViewById(R.id.potencia);
        modulo = (Button) findViewById(R.id.modulo);
        cociente = (Button) findViewById(R.id.cociente);
        reiniciar = (Button) findViewById(R.id.reiniciar);
        resultado = (TextView) findViewById(R.id.resultado);

        // Asignar los listeners
        sumar.setOnClickListener(this);
        restar.setOnClickListener(this);
        dividir.setOnClickListener(this);
        multiplicar.setOnClickListener(this);
        potencia.setOnClickListener(this);
        modulo.setOnClickListener(this);
        cociente.setOnClickListener(this);
        reiniciar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        primero = uno.getText().toString();
        segundo = dos.getText().toString();

        if (!primero.isEmpty() && !segundo.isEmpty()) {
            n1 = Double.parseDouble(primero);
            n2 = Double.parseDouble(segundo);

            if(view.getId() == R.id.sumar) {
                resultado.setText(primero + " + " + segundo + " = " + (n1 + n2));
            }
            else if(view.getId() == R.id.restar) {
                resultado.setText(primero + " - " + segundo + " = " + (n1 - n2));
            }
            else if(view.getId() == R.id.multiplicar) {
                resultado.setText(primero + " * " + segundo + " = " + (n1 * n2));
            }
            else if(view.getId() == R.id.dividir) {
                if (n2 != 0) {
                    resultado.setText(primero + " / " + segundo + " = " + (n1 / n2));
                } else {
                    resultado.setText("Error: División por 0");
                }
            }
            else if(view.getId() == R.id.potencia) {
                resultado.setText(primero + " ^ " + segundo + " = " + Math.pow(n1, n2));
            }
            else if(view.getId() == R.id.modulo) {
                resultado.setText(primero + " % " + segundo + " = " + (n1 % n2));
            }
            else if(view.getId() == R.id.cociente) {
                if (n2 != 0) {
                    resultado.setText(primero + " div " + segundo + " = " + (int)(n1 / n2));
                } else {
                    resultado.setText("Error: División por 0");
                }
            }
        } else {
            resultado.setText("Por favor, introduce ambos números");
        }

        if (view.getId() == R.id.reiniciar) {
            uno.setText("");
            dos.setText("");
            resultado.setText("0");
        }
    }
}
