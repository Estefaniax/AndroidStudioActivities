package com.example.spinner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    Spinner animales1, animales2;
    String[] opciones = {"Mono","Nutria","Perro","Gato"};
    int spinner1 = 0 , spinner2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animales1 = findViewById(R.id.animales1);
        animales2 = findViewById(R.id.animales2);

        ArrayAdapter<String> aa = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, opciones);
        animales1.setAdapter(aa);
        animales2.setAdapter(aa);
        animales1.setOnItemSelectedListener(this);
        animales2.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       spinner1 = position;
       if(parent.getId() == R.id.animales1){
           Toast.makeText(this, "Seleccionaste: " + opciones[spinner1], Toast.LENGTH_SHORT).show();
       }
       spinner2 = position;
       if(parent.getId() == R.id.animales2){
           Toast.makeText(MainActivity.this, "Seleccionaste: " + opciones[spinner2], Toast.LENGTH_SHORT).show();
       }

    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}