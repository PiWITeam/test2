package com.example.admin.test2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //Sucursal
        Spinner sucursalSpinner = (Spinner) findViewById(R.id.sucursalDrop);
        String[] sucursal = {"A","B","C","D","E"};
        sucursalSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sucursal));

        //Area
        Spinner areaSpinner = (Spinner) findViewById(R.id.areaDrop);
        String[] area = {"1","2","3","4","5"};
        areaSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, area));

        //equipo
        Spinner eqpSpinner = (Spinner) findViewById(R.id.eqpDrop);
        String[] eqp = {"Uno","Dos","Tres","Cuatro","Cinco"};
        eqpSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, eqp));

        //Resultados
        Button searchButton=(Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Results.class);
                startActivity(i);
            }
        });

    }
}
