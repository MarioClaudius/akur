package com.example.akurandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment implements View.OnClickListener {
    private CircleImageView storeLogo;
    private TextView tvStoreName;
    private TextView tvStoreJoin;
    private Button btnEditProfile;
    private Button btnEditShipment;
    private Button btnChangePassword;
    private Button btnLogout;

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
        btnEditProfile.setOnClickListener(this);
        btnEditShipment.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        Bundle bundle = getArguments();
        if(bundle != null){
            String storeName = bundle.getString("store name");
            if(storeName != null){
                tvStoreName.setText(storeName);
            }
        }
        return v;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btn_edit_profile:
                intent = new Intent(getActivity(), EditProfileActivity.class);
                break;

            case R.id.btn_edit_shipment:
                intent = new Intent(getActivity(), EditShipmentActivity.class);
                break;

            case R.id.btn_change_password:
                intent = new Intent(getActivity(), ChangePasswordActivity.class);
                break;

            case R.id.btn_logout:
                //intent = new Intent();
                break;
        }
        startActivity(intent);
    }
}
