package com.example.actividadas2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView texto;
    Button[] numberButtons = new Button[10];
    Button sumar, restar, multiplicar, dividir, igual, reset, potencia, cambiarSigno;
    int firstNum = 0, secondNum = 0;
    String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 10; i++) {
            String buttonID = "btn" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            numberButtons[i] = findViewById(resID);
            numberButtons[i].setOnClickListener(this);
        }

        sumar = findViewById(R.id.btnSumar);
        restar = findViewById(R.id.btnRestar);
        multiplicar = findViewById(R.id.btnMultiplicar);
        dividir = findViewById(R.id.btnDividir);
        igual = findViewById(R.id.btnIgual);
        reset = findViewById(R.id.btnReset);
        potencia = findViewById(R.id.btnPotencia);
        cambiarSigno = findViewById(R.id.btnCambiarSigno);
        texto = findViewById(R.id.txt);

        sumar.setOnClickListener(this);
        restar.setOnClickListener(this);
        multiplicar.setOnClickListener(this);
        dividir.setOnClickListener(this);
        igual.setOnClickListener(this);
        reset.setOnClickListener(this);
        potencia.setOnClickListener(this);
        cambiarSigno.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnIgual) {
            secondNum = Integer.parseInt(texto.getText().toString());
            int result = 0;
            switch (operator) {
                case "+":
                    result = firstNum + secondNum;
                    break;
                case "-":
                    result = firstNum - secondNum;
                    break;
                case "*":
                    result = firstNum * secondNum;
                    break;
                case "/":
                    if (secondNum != 0) {
                        result = firstNum / secondNum;
                    } else {
                        texto.setText("Error");
                        return;
                    }
                    break;
                case "^":
                    result = (int) Math.pow(firstNum, secondNum);
                    break;
            }
            texto.setText(String.valueOf(result));
        } else if (view.getId() == R.id.btnSumar || view.getId() == R.id.btnRestar
                || view.getId() == R.id.btnMultiplicar || view.getId() == R.id.btnDividir
                || view.getId() == R.id.btnPotencia) {
            firstNum = Integer.parseInt(texto.getText().toString());
            if (view.getId() == R.id.btnSumar) {
                operator = "+";
            } else if (view.getId() == R.id.btnRestar) {
                operator = "-";
            } else if (view.getId() == R.id.btnMultiplicar) {
                operator = "*";
            } else if (view.getId() == R.id.btnDividir) {
                operator = "/";
            } else if (view.getId() == R.id.btnPotencia) {
                operator = "^";
            }
            texto.setText("0");
        } else if (view.getId() == R.id.btnReset) {
            firstNum = 0;
            secondNum = 0;
            texto.setText("0");
        } else if (view.getId() == R.id.btnCambiarSigno) {
            String currentText = texto.getText().toString();
            if (!currentText.equals("0")) {
                if (currentText.startsWith("-")) {
                    texto.setText(currentText.substring(1));
                } else {
                    texto.setText("-" + currentText);
                }
            }
        } else {
            String currentText = texto.getText().toString();
            if (currentText.equals("0")) {
                texto.setText(((Button) view).getText().toString());
            } else {
                texto.setText(currentText + ((Button) view).getText().toString());
            }
        }
    }
}
