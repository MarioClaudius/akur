package com.example.akurandroid;

import java.util.ArrayList;

public class ShipmentData {
    private static String[] shipmentName = {
            "JNE",
            "Si Cepat",
            "JNT",
            "Wahana",
            "Ninja Express",
            "Anteraja",
            "ID Express",
            "Lion Parcel"
    };

    static ArrayList<Shipment> getListData(){
        ArrayList<Shipment> list = new ArrayList<>();
        for(int pos = 0; pos < shipmentName.length; pos++){
            Shipment shipment = new Shipment();
            shipment.setName(shipmentName[pos]);
            list.add(shipment);
        }
        return list;
    }
}
