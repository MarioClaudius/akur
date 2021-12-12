package com.akur.akurandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class EditShipmentActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rvEditShipment;
    private ImageButton backButton;
    private ArrayList<Shipment> list = new ArrayList<Shipment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shipment);

        rvEditShipment = findViewById(R.id.rv_edit_shipment);
        backButton = findViewById(R.id.back_btn_edit_profile);
        rvEditShipment.setHasFixedSize(true);

        list.addAll(ShipmentData.getListData());
        rvEditShipment.setLayoutManager(new LinearLayoutManager(this));
        ListShipmentAdapter listShipmentAdapter = new ListShipmentAdapter(list);
        rvEditShipment.setAdapter(listShipmentAdapter);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}