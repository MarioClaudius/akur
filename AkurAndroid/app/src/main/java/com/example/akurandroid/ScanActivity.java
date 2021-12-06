package com.example.akurandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, View.OnClickListener {
    private List<ScanFormat> formatList = new ArrayList<>();
    private TextView txtResult;
    private ZXingScannerView scannerView;
    private ImageButton flashButton;
    private boolean flashIsOn = false;
    private Bitmap mBitmap;
    private String courierName = null;

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
                    .inflate(R.layout.bottom_sheet_layout, (LinearLayout) findViewById(R.id.bottomSheetContainer));
            bottomSheetDialog.setCancelable(false);                 //bottomSheet tidak bisa di-cancel pakai back button
            TextView barcodeNumber = bottomSheetView.findViewById(R.id.barcode_number);
            MaterialButton yesButton = bottomSheetView.findViewById(R.id.yes_input_button);
            MaterialButton noButton = bottomSheetView.findViewById(R.id.no_input_button);
            barcodeNumber.setText(rawResult.getText());
            Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.comfortaa);
            barcodeNumber.setTypeface(typeface);
            //ambil gambar barcode
            ImageView barcode = bottomSheetView.findViewById(R.id.barcode);
            TextView tvShipment = bottomSheetView.findViewById(R.id.shipment_delivery);
            TextView tvReceiptNumber = bottomSheetView.findViewById(R.id.receipt_number);
            tvReceiptNumber.setText(rawResult.getText());
            generateBarCode(rawResult.getText(), barcode);

            ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
            Call<List<ScanFormat>> call = apiInterface.getFormatList();
            call.enqueue(new Callback<List<ScanFormat>>() {
                @Override
                public void onResponse(Call<List<ScanFormat>> call, Response<List<ScanFormat>> response) {
                    formatList = response.body();
                    courierName = null;
                    for(ScanFormat sf : formatList){
                        int startIndex = sf.getStarting();
                        int endIndex = sf.getEnding();
                        String format = sf.getFormat();
                        String str = rawResult.getText();
                        String substr = str.substring(startIndex, endIndex + 1);
                        if(rawResult.getText().substring(startIndex, endIndex + 1).equals(format)){
                            tvShipment.setText(sf.getNama_kurir());
                            courierName = sf.getNama_kurir();
                            Log.d("FORMAT", sf.getNama_kurir());
                            return;
                        }
                    }
                    if(courierName == null){
                        tvShipment.setText("other");
                        courierName = "other";
                    }
                    return;
                }

                @Override
                public void onFailure(Call<List<ScanFormat>> call, Throwable t) {

                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = getIntent().getIntExtra("idUser", 0);
                    ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
                    Call<Boolean> call = apiInterface.insertScan(id, courierName, rawResult.getText());
                    Log.d("RAWRESULT1", rawResult.getText());
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            boolean isInserted = response.body();
                            if(isInserted){
                                Toast.makeText(ScanActivity.this, "Insert successful", Toast.LENGTH_SHORT).show();
                                bottomSheetDialog.dismiss();
                                scannerView.resumeCameraPreview(ScanActivity.this::handleResult);
                            }
                            else {
                                Toast.makeText(ScanActivity.this, "Barcode already exists / Barcode invalid", Toast.LENGTH_SHORT).show();
                                bottomSheetDialog.dismiss();
                                scannerView.resumeCameraPreview(ScanActivity.this::handleResult);
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Toast.makeText(ScanActivity.this, "Insert failed", Toast.LENGTH_SHORT).show();
                            bottomSheetDialog.dismiss();
                            scannerView.resumeCameraPreview(ScanActivity.this::handleResult);
                        }
                    });
                    bottomSheetDialog.dismiss();
                    scannerView.resumeCameraPreview(ScanActivity.this::handleResult);

                }
            });
            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetDialog.dismiss();
                    scannerView.resumeCameraPreview(ScanActivity.this::handleResult);
                }
            });

            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        }
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

    public void generateBarCode(String data, ImageView mImageView){
        com.google.zxing.Writer c9 = new Code128Writer();
        try {
            BitMatrix bm = c9.encode(data, BarcodeFormat.CODE_128,500, 150);
            mBitmap = Bitmap.createBitmap(500, 150, Bitmap.Config.ARGB_8888);

            for (int i = 0; i < 500; i++) {
                for (int j = 0; j < 150; j++) {

                    mBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        if (mBitmap != null) {
            mImageView.setImageBitmap(mBitmap);
        }
    }
}