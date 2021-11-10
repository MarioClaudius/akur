package com.example.akurandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class EditShipmentActivity extends AppCompatActivity {
    private RecyclerView rvEditShipment;
    private ArrayList<Shipment> list = new ArrayList<Shipment>();
    private ArrayList<Shipment> newList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shipment);

        rvEditShipment = findViewById(R.id.rv_edit_shipment);
        rvEditShipment.setHasFixedSize(true);

        list.addAll(ShipmentData.getListData());
        rvEditShipment.setLayoutManager(new LinearLayoutManager(this));
        ListShipmentAdapter listShipmentAdapter = new ListShipmentAdapter(list);
        rvEditShipment.setAdapter(listShipmentAdapter);
        rvEditShipment.suppressLayout(true);
    }
}