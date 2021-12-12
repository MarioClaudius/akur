package com.akur.akurandroid;

public class ScanFormat {
    private int id_kurir;
    private String nama_kurir;
    private String format;
    private int starting;
    private int ending;

    public int getId_kurir() {
        return id_kurir;
    }

    public void setId_kurir(int id_kurir) {
        this.id_kurir = id_kurir;
    }

    public String getNama_kurir() {
        return nama_kurir;
    }

    public void setNama_kurir(String nama_kurir) {
        this.nama_kurir = nama_kurir;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getStarting() {
        return starting;
    }

    public void setStarting(int starting) {
        this.starting = starting;
    }

    public int getEnding() {
        return ending;
    }

    public void setEnding(int ending) {
        this.ending = ending;
    }
}
