package com.akur.akurandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    private ImageButton backButton;
    private EditText edtUsernameEditProfile;
    private EditText edtPhoneNumberEditProfile;
    private TextView edit;
    private Button saveButton;
    private ImageView logo;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backButton = findViewById(R.id.back_btn_edit_profile);
        edtUsernameEditProfile = findViewById(R.id.edt_username_editprofile);
        edtPhoneNumberEditProfile = findViewById(R.id.edt_numberphone_editprofile);
        edit = findViewById(R.id.tv_edit);
        saveButton = findViewById(R.id.btn_save_editprofile);
        logo = findViewById(R.id.store_logo_editprofile);
        edtUsernameEditProfile.setText(getIntent().getStringExtra("storeEdit"));
        edtPhoneNumberEditProfile.setText(getIntent().getStringExtra("phoneNumberEdit"));

        backButton.setOnClickListener(this::backToAccountActivity);
        saveButton.setOnClickListener(this::saveInformation);
    }

    public void saveInformation(View v){
        String storeName = edtUsernameEditProfile.getText().toString();
        String phoneNumber = edtPhoneNumberEditProfile.getText().toString();
        int id = getIntent().getIntExtra("idEdit", 0);
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<Boolean> call = apiInterface.updateAkurAccountInfo(id, storeName, phoneNumber);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                boolean isSuccess = response.body();
                if(isSuccess){
                    Toast.makeText(EditProfileActivity.this, "Update berhasil dilakukan", Toast.LENGTH_SHORT).show();
                    handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                            finish();
                        }
                    },1000);
                }
                else {
                    Toast.makeText(EditProfileActivity.this, "Update gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Masuk onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void backToAccountActivity(View v){
        onBackPressed();
    }
}