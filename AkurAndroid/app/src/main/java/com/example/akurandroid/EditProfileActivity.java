package com.example.akurandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class EditProfileActivity extends AppCompatActivity {
    private ImageButton backButton;
    private EditText edtUsernameEditProfile;
    private EditText edtPhoneNumberEditProfile;
    private TextView edit;
    private Button saveButton;
    private ImageView logo;

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

        backButton.setOnClickListener(this::backToAccountActivity);
        saveButton.setOnClickListener(this::saveInformation);
    }

    public void saveInformation(View v){
        String storeName = edtUsernameEditProfile.getText().toString();
        if(storeName != null){
            Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
            Log.d("MARIO", storeName);
            intent.putExtra("EXTRA_STORE_NAME", storeName);
            startActivity(intent);
        }
//        Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
//        intent.putExtra("EXTRA_STORE_NAME", storeName);
//        startActivity(intent);
//        Bundle bundle = new Bundle();
//        bundle.putString("store", storeName);
//        AccountFragment fragment = new AccountFragment();
//        fragment.setArguments(bundle);
    }

    public void backToAccountActivity(View v){
        onBackPressed();
    }
}