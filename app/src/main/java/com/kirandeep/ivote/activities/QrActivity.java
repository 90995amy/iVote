package com.kirandeep.ivote.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.kirandeep.ivote.R;

import java.util.List;

public class QrActivity extends AppCompatActivity {

    private DecoratedBarcodeView barcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        barcodeView = findViewById(R.id.barcode_scanner);

        barcodeView.decodeContinuous(callback);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            return;
        }

    }

    private static final int CAMERA_PERMISSION_CODE = 123;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            String aadharDetails = result.getText();
            if (aadharDetails != null) {
                barcodeView.setStatusText("Barcode Scanned: " + aadharDetails);
                Intent intent = new Intent();
                intent.putExtra("AadharDetails",aadharDetails);
                setResult(RESULT_OK,intent);
                barcodeView.pause();
                finish();

                barcodeView.resume();
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };



    @Override
    protected void onResume() {
        super.onResume();

        barcodeView.resume();
    }


    @Override
    protected void onPause() {
        super.onPause();

        barcodeView.pause();
    }

    private void pause(View view) {
        barcodeView.pause();
    }

    private void resume(View view) {
        barcodeView.resume();
    }

    private void triggerScan(View view) {
        barcodeView.decodeSingle(callback);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Scanner Started", Toast.LENGTH_LONG).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }



}
