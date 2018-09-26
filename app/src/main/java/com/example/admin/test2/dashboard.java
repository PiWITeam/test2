package com.example.admin.test2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

public class dashboard extends AppCompatActivity {
    CardView qrScanButton;
    CardView searchButton;
    CardView infoButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        qrScanButton = (CardView) findViewById(R.id.qrScanButton);
        qrScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScanButton.setClickable(false);
                searchButton.setClickable(false);
                infoButton.setClickable(false);
                Intent i = new Intent(getApplicationContext(),QRScan.class);
                startActivity(i);
            }
        });

        searchButton = (CardView) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScanButton.setClickable(false);
                searchButton.setClickable(false);
                infoButton.setClickable(false);
                Intent i = new Intent(getApplicationContext(),Search.class);
                startActivity(i);
            }
        });
        infoButton = (CardView) findViewById(R.id.infoButton);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScanButton.setClickable(false);
                searchButton.setClickable(false);
                infoButton.setClickable(false);
                Intent i = new Intent(getApplicationContext(),information.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();

        qrScanButton.setClickable(true);
        searchButton.setClickable(true);
        infoButton.setClickable(true);
    }
}
