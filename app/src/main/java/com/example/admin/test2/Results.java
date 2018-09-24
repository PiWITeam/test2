package com.example.admin.test2;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class Results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent i = getIntent();
        JSONObject equipo = null;
        try {
            equipo = new JSONObject(i.getStringExtra("equipo"));
        } catch (JSONException e){
            e.printStackTrace();
        }

        String[] equipoInfoKeys = new String[equipo.length()];
        Iterator<String> iter = equipo.keys();
        for(int index = 0; iter.hasNext(); index++){
            equipoInfoKeys[index] = iter.next();
        }



        TableLayout table = new TableLayout(this);

        table.setStretchAllColumns(true);
        table.setShrinkAllColumns(true);

        TableRow rowTitle = new TableRow(this);
        rowTitle.setGravity(Gravity.CENTER_HORIZONTAL);


        TableRow rowLabels = new TableRow(this);
        TableRow rowInfo = new TableRow(this);


        TextView empty = new TextView(this);

        // title column/row
        TextView title = new TextView(this);
        title.setText("Resultados");

        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 6;

        rowTitle.addView(title, params);
        table.addView(rowTitle);


        for (int j = 0; j <equipoInfoKeys.length ; j++) {
            TableRow row = new TableRow(this);
            TextView equipLabel = new TextView(this);
            equipLabel.setText(equipoInfoKeys[j]);
            equipLabel.setTypeface(Typeface.DEFAULT_BOLD);

            TextView valLabel = new TextView(this);
            valLabel.setText("Atlixco");
            valLabel.setTypeface(Typeface.SERIF);
            valLabel.setGravity(Gravity.CENTER_HORIZONTAL);

            row.addView(equipLabel);
            row.addView(valLabel);

            table.addView(row);

        }
        setContentView(table);

    }
}
