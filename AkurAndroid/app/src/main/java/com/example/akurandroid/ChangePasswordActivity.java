package com.example.akurandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText oldPasswordEdt;
    private EditText newPasswordEdt;
    private EditText confirmPasswordEdt;
    private ImageButton oldPasswordBtn;
    private ImageButton newPasswordBtn;
    private ImageButton confirmPasswordBtn;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPasswordEdt = findViewById(R.id.edt_oldpassword_changepw);
        newPasswordEdt = findViewById(R.id.edt_newpassword_changepw);
        confirmPasswordEdt = findViewById(R.id.edt_confirmpassword_changepw);
        oldPasswordBtn = findViewById(R.id.btn_old_password);
        newPasswordBtn = findViewById(R.id.btn_new_password);
        confirmPasswordBtn = findViewById(R.id.btn_confirm_password);
        confirm = findViewById(R.id.btn_confirm_changepw);
        confirm.setOnClickListener(this::comparePassword);
        oldPasswordBtn.setOnClickListener(this);
        newPasswordBtn.setOnClickListener(this);
        confirmPasswordBtn.setOnClickListener(this);
    }

    public void comparePassword(View v){
        String oldPassword = oldPasswordEdt.getText().toString();
        String newPassword = newPasswordEdt.getText().toString();
        String confirmPassword = confirmPasswordEdt.getText().toString();
        if((oldPassword.equals(getIntent().getStringExtra("oldPassword"))) && newPassword.equals(confirmPassword)){
            Toast.makeText(ChangePasswordActivity.this, "PASSWORD COCOK", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_old_password:
                changeInputType(oldPasswordEdt, oldPasswordBtn);
                break;

            case R.id.btn_new_password:
                changeInputType(newPasswordEdt, newPasswordBtn);
                break;

            case R.id.btn_confirm_password:
                changeInputType(confirmPasswordEdt, confirmPasswordBtn);
                break;
        }
    }

    public void changeInputType(EditText edt, ImageButton btn){
        if(edt.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
            edt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());     //show password
            btn.setImageResource(R.drawable.hidepass);
        }
        else{
            edt.setTransformationMethod(PasswordTransformationMethod.getInstance());        //hide password
            btn.setImageResource(R.drawable.logo_visible);
        }
    }
}