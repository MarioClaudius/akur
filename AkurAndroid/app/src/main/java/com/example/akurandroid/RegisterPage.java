package com.example.akurandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        signup.setOnClickListener(this::createAccount);

    }

    public void createAccount(View v){
        String usernameInput = edtUsername.getText().toString().trim();
        String emailInput = edtEmail.getText().toString().trim();
        String passwordInput = edtPassword.getText().toString().trim();
        boolean isMatch = false;
        for(AkurAccount a : MainActivity.list){
            if(a.getUsername().equals(usernameInput) || a.getEmail().equals(emailInput)){
                isMatch = true;
            }
        }
        if(!isMatch){
            ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
            Call<AkurAccount> call = apiInterface.createAkurAccount(usernameInput, emailInput, passwordInput);
            call.enqueue(new Callback<AkurAccount>() {
                @Override
                public void onResponse(Call<AkurAccount> call, Response<AkurAccount> response) {
                    AkurAccount akun = new AkurAccount(usernameInput, emailInput, passwordInput);
                    MainActivity.list.add(akun);
                    Toast.makeText(RegisterPage.this, "Akun berhasil dibuat!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<AkurAccount> call, Throwable t) {
                    Log.d("ERROR: ", t.getMessage());
                    Toast.makeText(RegisterPage.this, "Akun gagal dibuat!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(RegisterPage.this, "Akun sudah pernah dibuat!", Toast.LENGTH_SHORT).show();
        }
    }
}