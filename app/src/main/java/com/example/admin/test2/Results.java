package com.example.admin.test2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

import java.util.Iterator;

public class Results extends AppCompatActivity {
    RequestQueue queue;
    String url;
    JSONObject equipo = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //Recycler View
        RecyclerView rv = (RecyclerView)findViewById(R.id.recycleView);
        rv.setHasFixedSize(true);


        queue = Volley.newRequestQueue(this);
        url = "https://laboratorioasesores.com/NewSIIL/Mantenimiento/Development/";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url + "getMantenimientos.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                equipo=response;

                String[] equipoInfoKeys = new String[equipo.length()];
                Iterator<String> iter = equipo.keys();
                for(int index = 0; iter.hasNext(); index++){
                    equipoInfoKeys[index] = iter.next();
                }

//                TableLayout table = new TableLayout(Results.this);
//
//                table.setStretchAllColumns(true);
//                table.setShrinkAllColumns(true);
//
//                TableRow rowTitle = new TableRow(Results.this);
//                rowTitle.setGravity(Gravity.CENTER_HORIZONTAL);
//
//
//                TableRow rowLabels = new TableRow(Results.this);
//                TableRow rowInfo = new TableRow(Results.this);
//
//
//                TextView empty = new TextView(Results.this);
//
//                // title column/row
//                TextView title = new TextView(Results.this);
//                title.setText("Mantenimientos");
//
//                title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
//                title.setGravity(Gravity.CENTER);
//                title.setTypeface(Typeface.SERIF, Typeface.BOLD);
//
//                TableRow.LayoutParams params = new TableRow.LayoutParams();
//                params.span = 6;
//
//                rowTitle.addView(title, params);
//                table.addView(rowTitle);

                for (int j = 0; j <equipoInfoKeys.length ; j++) {
                    JSONObject res = new JSONObject();
                    try{
                         res= equipo.getJSONObject(equipoInfoKeys[j]);
                    }catch(JSONException e){
                        e.printStackTrace();
                    }

//                    TableRow row = new TableRow(Results.this);
//                    TextView equipLabel = new TextView(Results.this);
//                    equipLabel.setText(res.toString());
//
//                    TextView valLabel = new TextView(Results.this);
//
//                    //try
//                    valLabel.setText("Valor");
//
//                    valLabel.setTypeface(Typeface.SERIF);
//                    valLabel.setGravity(Gravity.CENTER_HORIZONTAL);
//
//                    row.addView(equipLabel);
//                    row.addView(valLabel);
//
//                    table.addView(row);
//                    setContentView(table);
                }
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



//        Intent i = getIntent();
//        JSONObject equipo = null;
//        try {
//            equipo = new JSONObject(i.getStringExtra("equipo"));
//        } catch (JSONException e){
//            e.printStackTrace();
//        }
    }
}
