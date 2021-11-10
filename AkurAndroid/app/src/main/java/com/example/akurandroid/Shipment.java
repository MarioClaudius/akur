package com.example.akurandroid;

import android.os.Parcel;
import android.os.Parcelable;

public class Shipment {
    private String name;
    private int logo;
    private boolean appear;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public boolean isAppear() {
        return appear;
    }

    public void setAppear(boolean appear) {
        this.appear = appear;
    }
}
