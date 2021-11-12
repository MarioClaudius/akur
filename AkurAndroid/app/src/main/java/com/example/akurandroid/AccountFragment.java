package com.example.akurandroid;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment implements View.OnClickListener {
    private CircleImageView storeLogo;
    private TextView tvStoreName;
    private TextView tvStoreJoin;
    private Button btnEditProfile;
    private Button btnEditShipment;
    private Button btnChangePassword;
    private Dialog dialog;
    private Button btnLogout;
    private String namaToko;

    public AccountFragment(){

    }

    public static AccountFragment newInstance(String storeName, String username, String email){
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString("store", storeName);
        args.putString("usernameBundle", username);
        args.putString("emailBundle", email);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        storeLogo = v.findViewById(R.id.store_logo);
        tvStoreName = v.findViewById(R.id.store_name);
        tvStoreJoin = v.findViewById(R.id.store_join);
        btnEditProfile = v.findViewById(R.id.btn_edit_profile);
        btnEditShipment = v.findViewById(R.id.btn_edit_shipment);
        btnChangePassword = v.findViewById(R.id.btn_change_password);
        btnLogout = v.findViewById(R.id.btn_logout);
        dialog = new Dialog(getActivity());
        btnEditProfile.setOnClickListener(this);
        btnEditShipment.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        if(getArguments() != null){
            this.namaToko = getArguments().getString("store");
        }
        tvStoreName.setText(this.namaToko);
        return v;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btn_edit_profile:
                intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_edit_shipment:
                intent = new Intent(getActivity(), EditShipmentActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_change_password:
                intent = new Intent(getActivity(), ChangePasswordActivity.class);
                intent.putExtra("username", getArguments().getString("usernameBundle"));
                intent.putExtra("email", getArguments().getString("emailBundle"));
                startActivity(intent);
                break;

            case R.id.btn_logout:
                logoutDialog();
                break;
        }

    }

    public void logoutDialog(){
        dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tv = dialog.findViewById(R.id.logout_sentence);
        MaterialButton yesButton = dialog.findViewById(R.id.yes_button);
        MaterialButton noButton = dialog.findViewById(R.id.no_button);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), LoginPage.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "Dialog NO", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    public void onBackPressed(){

        getActivity().moveTaskToBack(true);

    }
}
