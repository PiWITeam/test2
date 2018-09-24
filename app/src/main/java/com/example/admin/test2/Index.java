package com.example.admin.test2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.Intent;


public class Index extends AppCompatActivity {
    ImageButton qrScanButton;
    ImageButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        qrScanButton=(ImageButton)findViewById(R.id.scanButton);
        searchButton=(ImageButton)findViewById(R.id.searchButton);

        //QR Scan
        qrScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScanButton.setClickable(false);
                searchButton.setClickable(false);
                Intent i = new Intent(getApplicationContext(),QRScan.class);
                startActivity(i);
            }
        });

        //Manual search

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScanButton.setClickable(false);
                searchButton.setClickable(false);
                Intent i = new Intent(getApplicationContext(),Search.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();

        qrScanButton.setClickable(true);
        searchButton.setClickable(true);
    }
}
