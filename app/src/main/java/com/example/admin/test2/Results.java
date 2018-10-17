package com.example.admin.test2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.support.v7.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Results extends AppCompatActivity {
    ArrayList<MantConstructor> listaMantenimientos;
    RecyclerView recyclerMantenimientos;
    RequestQueue queue;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        listaMantenimientos = new ArrayList<>();
        recyclerMantenimientos = (RecyclerView) findViewById(R.id.recycleView1);
        recyclerMantenimientos.setLayoutManager(new LinearLayoutManager(this));
        queue = Volley.newRequestQueue(this);
        url = "https://laboratorioasesores.com/NewSIIL/Mantenimiento/Development/";

        fillMantenimientos();

    }
    public void fillMantenimientos(){


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url + "getMantenimientos.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject item;
                int responseLength = response.length() - 1;
                for(int index = 0; index < responseLength; index++){
                    String indexStr = Integer.toString(index);
                    try {
                        item = response.getJSONObject(indexStr);
                        String id = item.getString("Id");
                        String sucursal = item.getString("Sucursal");
                        String area = item.getString("Area");
                        String nombre = item.getString("Nombre")+ ":" + item.getString("Id");
                        String tipo = item.getString("Tipo");
                        String empresa = item.getString("Empresa");
                        String fecha = item.getString("Fecha");
                        listaMantenimientos.add(new MantConstructor(Integer.parseInt(id), sucursal, area, nombre, tipo, empresa, fecha, item));
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }

                Adapter adapter = new Adapter(listaMantenimientos);
                adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(JSONObject item) {
                        AlertDialog.Builder mantDialogBuilder = new AlertDialog.Builder(Results.this);

                        TableLayout table = new TableLayout(Results.this);
                        table.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                        table.setGravity(Gravity.CENTER);
                        table.setShrinkAllColumns(true);
                        table.setBaselineAligned(true);
                        TableLayout.LayoutParams verticalParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);


                        Iterator<String> iter = item.keys();

                        for (;iter.hasNext();) {
                            String valueKey = iter.next();
                            TableRow row = new TableRow(Results.this);
                            row.setLayoutParams(verticalParams);
                            row.setPadding(40,10,40,0);
                            row.setGravity(Gravity.CENTER);

                            TextView equipLabel = new TextView(Results.this);
                            equipLabel.setText(valueKey);
                            equipLabel.setTypeface(Typeface.DEFAULT_BOLD);
                            equipLabel.setTextColor(Color.parseColor("#FFFFFF"));
                            equipLabel.setBackgroundColor(Color.parseColor("#000000"));
                            equipLabel.setPadding(15,1,15,1);

                            TextView valLabel = new TextView(Results.this);
                            try{
                                if(valueKey.equals("Nombre")) {
                                    valLabel.setText(item.getString(valueKey) + ":" + item.getString("Id"));
                                } else {
                                    valLabel.setText(item.getString(valueKey));
                                }
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                            valLabel.setTypeface(Typeface.SERIF);
                            valLabel.setGravity(Gravity.CENTER_HORIZONTAL);
                            valLabel.setBackgroundColor(Color.parseColor("#EEEEEE"));
                            valLabel.setPadding(15,1,15,1);

                            row.addView(equipLabel);
                            row.addView(valLabel);

                            table.addView(row);
                        }

                        mantDialogBuilder.setTitle("Informacion Mantenimiento");
                        mantDialogBuilder.setView(table);
                        mantDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        mantDialogBuilder.show();
                    }
                });
                recyclerMantenimientos.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Results.this);
                builder.setTitle("Error");
                builder.setMessage(error.getMessage());
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
