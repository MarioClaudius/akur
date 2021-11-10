package com.example.akurandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {
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
    }

    public void comparePassword(View v){
        String oldPassword = oldPasswordEdt.getText().toString();
        if(oldPassword.equals(getIntent().getStringExtra("oldPassword"))){
            Toast.makeText(ChangePasswordActivity.this, "PASSWORD COCOK", Toast.LENGTH_SHORT).show();
        }
        oldPasswordEdt.setInputType(InputType.TYPE_CLASS_TEXT);
    }
}