package com.example.akurandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtPassword;
    private Button login;
    private TextView tvCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        login = findViewById(R.id.btn_login);
        tvCreateAccount = findViewById(R.id.tv_create_account);

        tvCreateAccount.setOnClickListener(this::goToRegisterPage);
        login.setOnClickListener(this::checkLoginCredential);
    }

    public void checkLoginCredential(View v){
        String usernameInput = edtEmail.getText().toString().trim();
        String passwordInput = edtPassword.getText().toString().trim();
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<AkurAccount> call = apiInterface.getAkurAccountId(usernameInput, passwordInput);
        call.enqueue(new Callback<AkurAccount>() {
            @Override
            public void onResponse(Call<AkurAccount> call, Response<AkurAccount> response) {
                AkurAccount account = response.body();
                int id = account.getId();
                String username = account.getUsername();
                if(id != -1){
                    Toast.makeText(LoginPage.this, "Login Berhasil!", Toast.LENGTH_SHORT).show();
                    Intent moveAkur = new Intent(LoginPage.this, MainActivity.class);
                    moveAkur.putExtra("id", id);
                    moveAkur.putExtra("usernameIntent", username);
                    startActivity(moveAkur);
                    return;
                }
                else {
                    Toast.makeText(LoginPage.this, "Email / Password tidak cocok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AkurAccount> call, Throwable t) {
                Toast.makeText(LoginPage.this, "Login gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToRegisterPage(View v){
        Intent registerIntent = new Intent(LoginPage.this, RegisterPage.class);
        startActivity(registerIntent);
    }

    public void onBackPressed(){
        moveTaskToBack(true);
    }
}