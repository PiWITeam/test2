package com.example.admin.test2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import javax.xml.transform.Result;

public class Results extends AppCompatActivity {
    ArrayList<MantConstructor> listaMantenimientos;
    RecyclerView recyclerMantenimientos;
    RequestQueue queue;
    String url;
    Toolbar resultsToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        resultsToolbar = findViewById(R.id.toolbar_results);
        setSupportActionBar(resultsToolbar);

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
                        table.setColumnShrinkable(0, true);
                        table.setColumnStretchable(1, true);
                        table.setBaselineAligned(true);

                        TableRow.LayoutParams verticalParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);


                        Iterator<String> iter = item.keys();

                        for (;iter.hasNext();) {
                            String valueKey = iter.next();
                            TableRow row = new TableRow(Results.this);
                            row.setLayoutParams(verticalParams);
                            row.setPadding(0,1,0,1);
                            row.setGravity(Gravity.CENTER);

                            TextView equipLabel = new TextView(Results.this);
                            equipLabel.setText(valueKey);
                            equipLabel.setTypeface(Typeface.DEFAULT_BOLD);
                            equipLabel.setTextColor(Color.parseColor("#FFFFFF"));
                            equipLabel.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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

                        TextView title = new TextView(Results.this);
                        title.setTypeface(Typeface.DEFAULT_BOLD);
                        title.setTextColor(Color.WHITE);
                        title.setTextSize(18);
                        title.setPadding(25,15,25,15);
                        title.setText("Informacion Mantenimiento");
                        title.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


                        LinearLayout dialogLayout = new LinearLayout(Results.this);
                        dialogLayout.setOrientation(LinearLayout.VERTICAL);
                        Button positive = new Button(Results.this);
                        positive.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        positive.setTextColor(Color.WHITE);
                        positive.animate();


                        final AlertDialog mantDialog = mantDialogBuilder.create();

                        positive.setText("Ok");
                        positive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mantDialog.dismiss();
                            }
                        });

                        dialogLayout.addView(table);
                        dialogLayout.addView(positive);

                        mantDialog.setView(dialogLayout);
                        mantDialog.setCustomTitle(title);

                        mantDialog.show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Intent i;
        switch (id){
            case R.id.action_menu:
                i = new Intent(getApplicationContext(),Dashboard.class);
                startActivity(i);
                break;
            case R.id.action_scan:
                i = new Intent(getApplicationContext(),QRScan.class);
                startActivity(i);
                break;
            case R.id.action_busqueda:
                i = new Intent(getApplicationContext(),Search.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
