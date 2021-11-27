package com.example.akurandroid;

import java.util.ArrayList;

public class ShipmentData {
    private static int[] userId = {
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0
    };

    private static String[] shipmentName = {
            "JNE",
            "SiCepat",
            "JNT",
            "Wahana",
            "Ninja Express",
            "AnterAja",
            "ID Express",
            "Lion Parcel"
    };

    private static int[] shipmentLogo = {
            R.drawable.jne,
            R.drawable.sicepat,
            R.drawable.jnt,
            R.drawable.wahana,
            R.drawable.ninjaexpress,
            R.drawable.anteraja,
            R.drawable.idexpress,
            R.drawable.lionexpress
    };

    public static boolean[] shipmentAppear = {
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true
    };

    static ArrayList<Shipment> getListData(){
        ArrayList<Shipment> list = new ArrayList<>();
        for(int pos = 0; pos < shipmentName.length; pos++){
            Shipment shipment = new Shipment();
            shipment.setName(shipmentName[pos]);
            shipment.setLogo(shipmentLogo[pos]);
            shipment.setAppear(shipmentAppear[pos]);
            list.add(shipment);
        }
        return list;
    }
}
