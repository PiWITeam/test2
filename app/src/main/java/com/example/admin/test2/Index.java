package com.example.admin.test2;

import android.app.ActivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;

import java.util.List;


public class Index extends AppCompatActivity {
    ImageButton qrScanButton;
    ImageButton searchButton;
    ImageButton logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        qrScanButton=findViewById(R.id.scanButton);
        searchButton=findViewById(R.id.searchButton);
        logoutButton=findViewById(R.id.logoutButton);

        //QR Scan
        qrScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScanButton.setClickable(false);
                searchButton.setClickable(false);
                logoutButton.setClickable(false);
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
                logoutButton.setClickable(false);
                Intent i = new Intent(getApplicationContext(),Search.class);
                startActivity(i);
            }
        });

        //LogOut
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScanButton.setClickable(false);
                searchButton.setClickable(false);
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
