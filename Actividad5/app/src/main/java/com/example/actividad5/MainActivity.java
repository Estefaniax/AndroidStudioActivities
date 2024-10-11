package com.example.actividad5;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText usuario, password;
    Button ingresar;
    String usu, pass;
    Toast mensaje;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Ingresa tus credenciales");

        usuario = (EditText) findViewById(R.id.usuario);
        password = (EditText) findViewById(R.id.password);
        ingresar = (Button) findViewById(R.id.ingresar);


        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usu = usuario.getText().toString();

                pass = password.getText().toString();

                // Notificaciones popup rápidas (LONG o SHORT)
                // mensaje = Toast.makeText(MainActivity.this, "Hola!", Toast.LENGTH_LONG);
                // mensaje.show();
                // otro uso de Toast es para debugging en tiempo de ejecución y mostrar contenido variables
                // NOTA: Remover en versión de producción
                // mensaje = Toast.makeText(MainActivity.this, usu+":"+pass, Toast.LENGTH_LONG);
                // mensaje.show();

                if (usu.equals("") || pass.equals("")) {
                    mensaje = Toast.makeText(MainActivity.this, "Favor de ingresar credenciales", Toast.LENGTH_LONG);
                } else if (usu.equals("Estefania") && pass.equals("123")) {
                    mensaje = Toast.makeText(MainActivity.this, "Bienvenid@", Toast.LENGTH_LONG);
                    i = new Intent(MainActivity.this, Bienvenida.class);
                    i.putExtra("pass", pass);
                    i.putExtra("usuario", usu);
                    startActivity(i);
                } else {
                    mensaje = Toast.makeText(MainActivity.this, "Usuario o contraseña incorrecto", Toast.LENGTH_LONG);
                }

                mensaje.show();



            }
        });


    }
}