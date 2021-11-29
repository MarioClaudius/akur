package com.example.akurandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPage extends AppCompatActivity {
    private ImageView logo;
    private TextView tvRegister;
    private TextView tvRegisterDetail;
    private TextView tvUsername;
    private EditText edtUsername;
    private TextView tvEmail;
    private TextView edtEmail;
    private TextView tvPassword;
    private EditText edtPassword;
    private CheckBox checkBox;
    private TextView tvAgree;
    private TextView tvPrivacyPolicy;
    private Button signup;
    private TextView tvTermOfService;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        logo = findViewById(R.id.logo_register);
        tvRegister = findViewById(R.id.tv_register);
        tvRegisterDetail = findViewById(R.id.tv_register_detail);
        tvUsername = findViewById(R.id.tv_username_register);
        edtUsername = findViewById(R.id.edt_username_register);
        tvEmail = findViewById(R.id.tv_email_register);
        edtEmail = findViewById(R.id.edt_email_register);
        tvPassword = findViewById(R.id.tv_password_register);
        edtPassword = findViewById(R.id.edt_password_register);
        checkBox = findViewById(R.id.checkbox_register);
        tvAgree = findViewById(R.id.tv_agree);
        tvPrivacyPolicy = findViewById(R.id.privacy_policy);
        signup = findViewById(R.id.btn_signup);
        tvTermOfService = findViewById(R.id.tv_term_of_service);
        dialog = new Dialog(this);
        checkBox.setOnClickListener(this::isChecked);
        signup.setOnClickListener(this::createAccount);
        tvPrivacyPolicy.setOnClickListener(this::createPrivacyPolicyDialog);
    }

    public void isChecked(View v){
        if(checkBox.isChecked()){
            signup.setEnabled(true);
        }
        else{
            signup.setEnabled(false);
        }
    }

    public void createPrivacyPolicyDialog(View v){
//        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_privacy_policy);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MaterialButton doneBtnPrivacyPolicy = dialog.findViewById(R.id.btn_privacy_policy);
        doneBtnPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void createAccount(View v){
        String usernameInput = edtUsername.getText().toString().trim();
        String emailInput = edtEmail.getText().toString().trim();
        String passwordInput = edtPassword.getText().toString().trim();
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<Boolean> call = apiInterface.createAkurAccount(usernameInput, emailInput, passwordInput);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                boolean isCreated = response.body();
                if(isCreated){
                    Toast.makeText(RegisterPage.this, "Akun berhasil dibuat!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(RegisterPage.this, "Akun sudah pernah dibuat!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("ERROR: ", t.getMessage());
                Toast.makeText(RegisterPage.this, "Akun gagal dibuat!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}