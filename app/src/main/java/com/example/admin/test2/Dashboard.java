package com.example.admin.test2;

import android.app.ActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

public class Dashboard extends AppCompatActivity {
    CardView qrScanButton;
    CardView searchButton;
    CardView infoButton;
    CardView logoutButton;
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
                logoutButton.setClickable(false);
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
                logoutButton.setClickable(false);
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
                logoutButton.setClickable(false);
                Intent i = new Intent(getApplicationContext(),Results.class);
                startActivity(i);
            }
        });

        logoutButton= (CardView) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScanButton.setClickable(false);
                searchButton.setClickable(false);
                infoButton.setClickable(false);
                logoutButton.setClickable(false);
                Intent i = new Intent(getApplicationContext(), Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
        logoutButton.setClickable(true);
    }
    @Override
    public void onBackPressed(){
        if(!shouldAllowBack()){
            super.onBackPressed();
        }
    }

    public boolean shouldAllowBack(){
        ActivityManager mngr = (ActivityManager) getSystemService( ACTIVITY_SERVICE );
        List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(1);
        if(taskList.get(0).numActivities == 2){
            return true;
        }

        return false;
    }
}
