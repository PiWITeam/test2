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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final Button searchButton= findViewById(R.id.searchButton);
        final RequestQueue queue = Volley.newRequestQueue(this);
        final Spinner sucursalSpinner = findViewById(R.id.sucursalDrop);
        final Spinner areaSpinner = findViewById(R.id.areaDrop);
        final Spinner eqpSpinner = findViewById(R.id.eqpDrop);
        final Object cacheItemSelected = new Object();
        final String url = "https://laboratorioasesores.com/NewSIIL/Mantenimiento/Development/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url + "getSucursal.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String[] sucursal = new String[response.length() + 1];
                sucursal[0] = "Selecciona Sucursal";
                Iterator<String> iter = response.keys();
                for(int index = 1; iter.hasNext(); index++){
                    try {
                        sucursal[index] = response.getString(iter.next());
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
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
        sucursalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(0 < position){
                    JSONObject jsonObject = new JSONObject();
                    try{
                        jsonObject.put("sucursal",sucursalSpinner.getSelectedItem().toString());
                    } catch(JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url + "getArea.php", jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String[] area = new String[response.length() + 1];
                            areaSpinner.setEnabled(true);
                            eqpSpinner.setEnabled(false);
                            area[0] = "Selecciona area";
                            Iterator<String> iter = response.keys();
                            for(int index = 1; iter.hasNext(); index++){
                                try {
                                    area[index] = response.getString(iter.next());
                                } catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                            areaSpinner.setAdapter(new ArrayAdapter<String>(Search.this, android.R.layout.simple_spinner_dropdown_item, area));
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
                    JSONObject jsonObject = new JSONObject();
                    try{
                        jsonObject.put("sucursal",sucursalSpinner.getSelectedItem().toString());
                        jsonObject.put("area",areaSpinner.getSelectedItem().toString());
                    } catch(JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url + "getEquipos.php", jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String[] eqp = new String[response.length() + 1];
                            eqpSpinner.setEnabled(true);
                            eqp[0] = "Selecciona area";
                            Iterator<String> iter = response.keys();
                            for(int index = 1; iter.hasNext(); index++){
                                try {
                                    String id = iter.next();
                                    eqp[index] = response.getString(id) + ':' + id;
                                } catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                            eqpSpinner.setAdapter(new ArrayAdapter<String>(Search.this, android.R.layout.simple_spinner_dropdown_item, eqp));
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
                } else {
                    eqpSpinner.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //equipo
        eqpSpinner.setEnabled(false);


        //Resultados
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eqpSpinner.getSelectedItemPosition() > 0) {
                    String eqp = eqpSpinner.getSelectedItem().toString();
                    String id = eqp.substring(eqp.indexOf(':') + 1);
                    JSONObject jsonObject = new JSONObject();
                    try{
                        jsonObject.put("id",id);
                    } catch(JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url + "getEquipo.php", jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Intent i = new Intent(getApplicationContext(), Results.class);
                            i.putExtra("equipo",response.toString());
                            startActivity(i);
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
                }
            }
        });

    }
}
