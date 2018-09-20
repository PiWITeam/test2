package com.example.admin.test2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.Intent;


public class Index extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        //QR Scan
        ImageButton qrScanButton=(ImageButton)findViewById(R.id.scanButton);
        qrScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),QRScan.class);
                startActivity(i);
            }
        });

        //Manual search
        ImageButton searchButton=(ImageButton)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Search.class);
                startActivity(i);
            }
        });

    }
}
