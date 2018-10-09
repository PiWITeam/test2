package com.example.admin.test2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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

import java.util.ArrayList;
import java.util.Iterator;

public class Results extends AppCompatActivity {
    ArrayList<MantConstructor> listaMantenimientos;
    RecyclerView recyclerMantenimientos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        listaMantenimientos = new ArrayList<>();
        recyclerMantenimientos = (RecyclerView) findViewById(R.id.recycleView1);
        recyclerMantenimientos.setLayoutManager(new LinearLayoutManager(this));

        fillMantenimientos();

        Adapter adapter = new Adapter(listaMantenimientos);
        recyclerMantenimientos.setAdapter(adapter);



    }
    public void fillMantenimientos(){
        for(int i = 0; i<5; i++){
            listaMantenimientos.add(new MantConstructor(i,"sucursal","area","nombre","tipo","empresa","fecha"));
        }
    }
}
