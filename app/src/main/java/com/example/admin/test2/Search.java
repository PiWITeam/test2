package com.example.admin.test2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        final RequestQueue queue = Volley.newRequestQueue(this);
        final Spinner sucursalSpinner = findViewById(R.id.sucursalDrop);
        final Spinner areaSpinner = findViewById(R.id.areaDrop);
        final Spinner eqpSpinner = findViewById(R.id.eqpDrop);
        final Object cacheItemSelected = new Object();

        String url = "https://laboratorioasesores.com/NewSIIL/Mantenimiento/Development/testcon.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray keys;
                String[] sucursal = {"Selecciona Sucursal", "A"};
                try{
                    keys = response.getJSONArray("keys");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                sucursal = {"Selecciona Sucursal"};
                sucursalSpinner.setAdapter(new ArrayAdapter<String>(Search.this, android.R.layout.simple_spinner_dropdown_item, sucursal));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Search.this);
                builder.setTitle("Error");
                builder.setMessage(error.getMessage());
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        queue.add(jsonObjectRequest);


        //Sucursal
        String[] sucursal = {"Selecciona Sucursal"};
        sucursalSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sucursal));
        sucursalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(0 < position){
                    String[] area = {"Selecciona Area","Uno","Dos","Tres","Cuatro","Cinco"};
                    areaSpinner.setAdapter(new ArrayAdapter<String>(Search.this, android.R.layout.simple_spinner_item, area));
                    areaSpinner.setEnabled(true);
                    eqpSpinner.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Area
        areaSpinner.setEnabled(false);
        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(0 < position) {
                    String[] eqp = {"Selecciona Equipo","Uno","Dos","Tres","Cuatro","Cinco"};
                    eqpSpinner.setAdapter(new ArrayAdapter<String>(Search.this, android.R.layout.simple_spinner_item, eqp));
                    eqpSpinner.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //equipo
        eqpSpinner.setEnabled(false);
//        eqpSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            }
//        });


        //Resultados
        Button searchButton= findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Results.class);
                startActivity(i);
            }
        });

    }
}
