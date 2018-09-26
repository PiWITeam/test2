package com.example.admin.test2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class Graph extends AppCompatActivity implements OnChartValueSelectedListener {

    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        pieChart = findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        List<PieEntry> pieListEntries = new ArrayList<PieEntry>();
        pieListEntries.add(new PieEntry(8f, "January"));
        pieListEntries.add(new PieEntry(15f, "February"));
        pieListEntries.add(new PieEntry(12f, "March"));
        pieListEntries.add(new PieEntry(25f, "April"));
        pieListEntries.add(new PieEntry(23f, "May"));
        pieListEntries.add(new PieEntry(17f, "June"));

        PieDataSet dataSet = new PieDataSet(pieListEntries, "Election Results");

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());

        pieChart.setData(data);

        Description pieDescription = new Description();
        pieDescription.setText("This is Pie Chart");
        pieChart.setDescription(pieDescription);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setTransparentCircleRadius(58f);pieChart.setHoleRadius(58f);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);

        pieChart.setOnChartValueSelectedListener(this);

        pieChart.animateXY(1400, 1400);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", xIndex: " + h.getX()
                        + ", DataSet index: " + h.getDataIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }
}
