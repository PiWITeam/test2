package com.example.admin.test2;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    protected static final int MY_PERMISSIONS_REQUEST_INTERNET = 1;
    Button loginButton;
    TextView userField;
    TextView passwordField;
    RequestQueue queue;

    static final String STATE_USER = "USER";
    static final String STATE_PASSWORD = "PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton= findViewById(R.id.loginButton);
        userField = findViewById((R.id.userField));
        passwordField = findViewById((R.id.passwordField));
        queue = Volley.newRequestQueue(this);

        View.OnClickListener loginClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setClickable(false);
                // Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(Login.this,
                        Manifest.permission.INTERNET)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(Login.this,
                            Manifest.permission.INTERNET)) {

                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                        loginButton.setClickable(true);
                    } else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(Login.this,
                                new String[]{Manifest.permission.READ_CONTACTS},
                                MY_PERMISSIONS_REQUEST_INTERNET);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                        loginButton.setClickable(true);
                    }
                } else {
                    String url = "https://laboratorioasesores.com/NewSIIL/Mantenimiento/Development/testcon.php";
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("user", userField.getText());
                        jsonObject.put("password", passwordField.getText());
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST ,url, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Intent i = new Intent(getApplicationContext(),dashboard.class);
                            startActivity(i);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                            builder.setTitle("Error");
                            builder.setMessage(error.getMessage());
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    loginButton.setClickable(true);
                                }
                            });

                            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    loginButton.setClickable(true);
                                }
                            });
                            builder.show();
                        }
                    });
                    queue.add(jsonObjectRequest);
                }
            }
        };

        loginButton.setOnClickListener(loginClickListener);
    }

    @Override
    public void onResume(){
        super.onResume();

        loginButton.setClickable(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_INTERNET: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
