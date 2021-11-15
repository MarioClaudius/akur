package com.example.akurandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import static android.Manifest.permission.CAMERA;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, View.OnClickListener {
    private TextView txtResult;
    private ZXingScannerView scannerView;
    private ImageButton flashButton;
    private boolean flashIsOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        scannerView = findViewById(R.id.zxscan);
        txtResult = findViewById(R.id.txt_result);
        flashButton = findViewById(R.id.flash_btn);
        flashButton.bringToFront();
        flashButton.setOnClickListener(this);

        Dexter.withActivity(this)
                .withPermission(CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        scannerView.setResultHandler(ScanActivity.this);
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(ScanActivity.this, "You must accept this permission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
    }

    protected void onDestroy(){
        scannerView.stopCamera();
        super.onDestroy();
    }

    @Override
    public void handleResult(Result rawResult) {
        //receive RawResult => nerima dari scannernya
        if(rawResult.getText() != null){
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ScanActivity.this, R.style.BottomSheetDialogTheme);
            View bottomSheetView = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.bottom_sheet_layout, findViewById(R.id.bottomSheetContainer));
            Button yesButton = bottomSheetView.findViewById(R.id.yes_input_button);
            Button noButton = bottomSheetView.findViewById(R.id.no_input_button);
            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ScanActivity.this, "YES", Toast.LENGTH_SHORT).show();
                    bottomSheetDialog.dismiss();
                    scannerView.resumeCameraPreview(ScanActivity.this::handleResult);

                }
            });
            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ScanActivity.this, "NO", Toast.LENGTH_SHORT).show();
                    bottomSheetDialog.dismiss();
                    scannerView.resumeCameraPreview(ScanActivity.this::handleResult);
                }
            });

            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
//            Intent backWithDataIntent = new Intent(ScanActivity.this, MainActivity.class);
//            backWithDataIntent.putExtra(/*MainActivity.EXTRA_SCAN_RESULT*/ "EXTRA_SCAN_RESULT", rawResult.getText());
//            startActivity(backWithDataIntent);
        }
//        txtResult.setText(rawResult.getText());
//        scannerView.resumeCameraPreview(ScanActivity.this);          //biar bisa jalanin kameranya setelah set Text

    }

    @Override
    public void onClick(View v) {
        scannerView.toggleFlash();
        if(flashIsOn){
            flashButton.setImageResource(R.drawable.ic_flash_on);
            flashIsOn = false;
        }
        else {
            flashButton.setImageResource(R.drawable.ic_flash_off);
            flashIsOn = true;
        }
    }
}