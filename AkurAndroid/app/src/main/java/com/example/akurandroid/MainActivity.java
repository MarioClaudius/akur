package com.example.akurandroid;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    public static List<AkurAccount> list = new ArrayList<AkurAccount>();
    public static ArrayList<String> usernameList = new ArrayList<String>();
    public static ArrayList<String> emailList = new ArrayList<String>();
    public static ArrayList<String> passwordList = new ArrayList<String>();

    private EditText edtEmail;
    private EditText edtPassword;
    private Button login;
    private ImageView logo;
    private TextView tvLogin;
    private TextView tvLoginDetail;
    private TextView tvEmail;
    private TextView tvPassword;
    private TextView tvForgotPassword;
    private CheckBox checkBox;
    private TextView tvRememberMe;
    private TextView tvNewOnPlatform;
    private TextView tvCreateAccount;
    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = findViewById(R.id.logo);
        tvLogin = findViewById(R.id.tv_login);
        tvLoginDetail = findViewById(R.id.tv_login_detail);
        tvEmail = findViewById(R.id.tv_email);
        edtEmail = findViewById(R.id.edt_email);
        tvPassword = findViewById(R.id.tv_password);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
        edtPassword = findViewById(R.id.edt_password);
        checkBox = findViewById(R.id.checkbox);
        tvRememberMe = findViewById(R.id.tv_remember_me);
        login = findViewById(R.id.btn_login);
        tvNewOnPlatform = findViewById(R.id.tv_new_on_platform);
        tvCreateAccount = findViewById(R.id.tv_create_account);
        tvTest = findViewById(R.id.tv_test);

        tvCreateAccount.setOnClickListener(this::goToRegisterPage);
        login.setOnClickListener(this::checkLoginCredential);
    }

    public void checkLoginCredential(View v){
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<AkurAccount>> call = apiInterface.getAkurAccount();
        call.enqueue(new Callback<List<AkurAccount>>() {
            @Override
            public void onResponse(Call<List<AkurAccount>> call, Response<List<AkurAccount>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    String emailInput = edtEmail.getText().toString().trim();
                    String passwordInput = edtPassword.getText().toString().trim();
//                    String line = "";
                    for(AkurAccount akun : list){
//                        line += "Username : " + akun.getUsername() + "\n";
//                        line += "Password : " + akun.getPassword() + "\n";
//                        line += "Email : " + akun.getEmail() + "\n";
//                        tvTest.setText(line);
                        if(akun.getEmail().equals(emailInput) && akun.getPassword().equals(passwordInput)){
                            Intent moveAkur = new Intent(MainActivity.this, RegisterPage.class);
                            startActivity(moveAkur);
                            return;
                        }
                    }
                    Toast.makeText(MainActivity.this, "Email / Password tidak cocok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AkurAccount>> call, Throwable t) {
                Log.d("ERROR: ", t.getMessage());
                Toast.makeText(MainActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToRegisterPage(View v){
        Intent registerIntent = new Intent(MainActivity.this, RegisterPage.class);
        startActivity(registerIntent);
    }
}