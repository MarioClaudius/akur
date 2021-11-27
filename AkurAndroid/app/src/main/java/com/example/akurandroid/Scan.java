package com.example.akurandroid;

public class Scan {
    private int id_qr;
    private String nama_kurir;
    private String date;
    private String no_resi;
    private String jenis_kurir;
    private String status;

    public int getId() {
        return id_qr;
    }

    public void setId(int id_qr) {
        this.id_qr = id_qr;
    }

    public String getCourierName() {
        return nama_kurir;
    }

    public void setCourierName(String nama_kurir) {
        this.nama_kurir = nama_kurir;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReceiptNumber(){
        return no_resi;
    }

    public void setReceiptNumber(String no_resi){
        this.no_resi = no_resi;
    }

    public String getCourierType(){
        return jenis_kurir;
    }

    public void setCourierType(String jenis_kurir){
        this.jenis_kurir = jenis_kurir;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
