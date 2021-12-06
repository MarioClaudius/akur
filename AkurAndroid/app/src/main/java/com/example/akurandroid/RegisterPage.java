package com.example.akurandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private Handler handler;
    public static final String REGEX_EMAIL = "^[a-zA-Z0-9&*_~]+(\\.[a-zA-Z0-9&*_~]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    public static final String REGEX_PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?!.* ).{8,}$";
    public static final Pattern REGEX_PATTERN_EMAIL = Pattern.compile(REGEX_EMAIL);
    public static final Pattern REGEX_PATTERN_PASSWORD = Pattern.compile(REGEX_PASSWORD);

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
        Matcher matcherEmail = REGEX_PATTERN_EMAIL.matcher(emailInput);
        Matcher matcherPassword = REGEX_PATTERN_PASSWORD.matcher(passwordInput);
        boolean isEmptyField = false;
        boolean isValid = matcherEmail.find() && matcherPassword.find();
        if(TextUtils.isEmpty(usernameInput)){
            isEmptyField = true;
            edtUsername.setError("Username is empty");
        }

        if(TextUtils.isEmpty(emailInput)){
            isEmptyField = true;
            edtEmail.setError("Email is empty");
        }

        if(TextUtils.isEmpty(passwordInput)){
            isEmptyField = true;
            edtEmail.setError("Password is empty");
        }

        if(isEmptyField){
            dialog.setContentView(R.layout.dialog_credential_failed);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            MaterialButton retryButton = dialog.findViewById(R.id.btn_retry_register);
            TextView tvErrorMsg = dialog.findViewById(R.id.tv_error_message_register);
            tvErrorMsg.setText("Please insert username, email, and password!");
            retryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else if(!isValid){
            dialog.setContentView(R.layout.dialog_credential_failed);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            MaterialButton retryButton = dialog.findViewById(R.id.btn_retry_register);
            retryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else {
            ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
            Call<Boolean> call = apiInterface.createAkurAccount(usernameInput, emailInput, passwordInput);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    boolean isCreated = response.body();
                    if(isCreated){
                        dialog.setContentView(R.layout.dialog_credential_success);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                        handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                Intent intent = new Intent(RegisterPage.this, LoginPage.class);
                                startActivity(intent);
                                finish();
                            }
                        },1500);
                    }
                    else {
                        dialog.setContentView(R.layout.dialog_credential_failed);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        TextView tvErrorMsg = dialog.findViewById(R.id.tv_error_message_register);
                        tvErrorMsg.setText("Username or email already exists!");
                        MaterialButton retryButton = dialog.findViewById(R.id.btn_retry_register);
                        retryButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.d("ERROR: ", t.getMessage());
                    dialog.setContentView(R.layout.dialog_credential_failed);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    TextView tvErrorMsg = dialog.findViewById(R.id.tv_error_message_register);
                    tvErrorMsg.setText("Username or email already exists!");
                    MaterialButton retryButton = dialog.findViewById(R.id.btn_retry_register);
                    retryButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
        }
    }
}