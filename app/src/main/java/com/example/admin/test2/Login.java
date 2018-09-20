package com.example.admin.test2;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton= findViewById(R.id.loginButton);
        final TextView userField = findViewById((R.id.userField));
        final TextView passwordField = findViewById((R.id.passwordField));
        final RequestQueue queue = Volley.newRequestQueue(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.1.84:3000/login";
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("user", userField.getText());
                    jsonObject.put("password", passwordField.getText());
                }catch (JSONException e){
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent i = new Intent(getApplicationContext(),Index.class);
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
            }
        });
    }
}
