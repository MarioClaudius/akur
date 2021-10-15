package com.example.akurandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
    }
}