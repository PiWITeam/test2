package com.example.admin.test2;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class QRScan extends AppCompatActivity {

    SurfaceView surfaceView;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    RequestQueue queue;
    String[] id = {""};

    private static final int CAMERA_PERMISSION_CAMERA = 0x000000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);

        if (ContextCompat.checkSelfPermission(QRScan
                        .this,
                Manifest
                        .permission
                        .CAMERA)
                != PackageManager
                .PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(QRScan
                            .this,
                    Manifest
                            .permission
                            .CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(QRScan
                                .this,
                        new String[]{Manifest.permission
                                .CAMERA},
                        CAMERA_PERMISSION_CAMERA);

                // CAMERA_PERMISSION_CAMERA is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        queue = Volley.newRequestQueue(this);
        surfaceView = findViewById(R.id.cameraPreview);
    }

    @Override
    public void onResume(){
        super.onResume();

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector).setAutoFocusEnabled(true).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try {
                    cameraSource.start(surfaceView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (cameraSource != null) {
                    cameraSource.release();
                    cameraSource = null;
                }
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if (qrcodes.size() != 0 && !id[0].equals(qrcodes.valueAt(0).rawValue.toString())) {
                    id[0] = qrcodes.valueAt(0).rawValue;
                    Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(1000);

                    String url = "https://laboratorioasesores.com/NewSIIL/Mantenimiento/Development/getEquipo.php";
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("id", id[0]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Intent i = new Intent(getApplicationContext(), Results.class);
                            i.putExtra("equipo", response.toString());
                            startActivity(i);
                            id[0] = "";
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(QRScan.this);
                            builder.setTitle("Error");
                            builder.setMessage(error.getMessage());
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    id[0] = "";
                                }
                            });

                            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    id[0] = "";
                                }
                            });
                            builder.show();
                        }
                    });
                    queue.add(jsonObjectRequest);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            cameraSource.stop();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0]
                        == PackageManager
                        .PERMISSION_GRANTED) {

                    Intent startMain = new Intent(QRScan
                            .this, QRScan
                            .class);
                    startActivity(startMain);

                } else {
                    if (ContextCompat.checkSelfPermission(QRScan
                                    .this,
                            Manifest
                                    .permission
                                    .CAMERA)
                            != PackageManager
                            .PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(QRScan
                                        .this,
                                Manifest
                                        .permission
                                        .CAMERA)) {

                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {

                            // No explanation needed, we can request the permission.

                            ActivityCompat.requestPermissions(QRScan
                                            .this,
                                    new String[]{Manifest.permission
                                            .CAMERA},
                                    CAMERA_PERMISSION_CAMERA);

                            // CAMERA_PERMISSION_CAMERA is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    }
                }
                return;
            }
        }
    }
}
