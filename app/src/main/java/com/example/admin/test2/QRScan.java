package com.example.admin.test2;

import android.Manifest;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;


public class QRScan extends AppCompatActivity {

    SurfaceView surfaceView;
    CameraSource cameraSource;
    TextView textView;
    BarcodeDetector barcodeDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);

        surfaceView= (SurfaceView)findViewById(R.id.cameraPreview);
        textView= (TextView)findViewById(R.id.textView);

        barcodeDetector=new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource= new CameraSource.Builder(this,barcodeDetector).setRequestedPreviewSize(640,480).setAutoFocusEnabled(true).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try {
                    cameraSource.start(holder);
                }catch(IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes=detections.getDetectedItems();
                if(qrcodes.size()!=0 ){
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator=(Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            textView.setText(qrcodes.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });


    }
}
