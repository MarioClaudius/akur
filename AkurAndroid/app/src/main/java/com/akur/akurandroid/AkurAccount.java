package com.akur.akurandroid;

public class AkurAccount {
    private int user_id;
    private String username;
    private String email;
    private String password;
    private String nama_toko;
    private String phone_number;

    public AkurAccount(){

    }

    public AkurAccount(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getId(){
        return user_id;
    }

    public void setId(int user_id){
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStoreName(){
        return nama_toko;
    }

    public void setStoreName(String nama_toko){
        this.nama_toko = nama_toko;
    }

    public String getPhoneNumber(){
        return phone_number;
    }

    public void setPhoneNumber(String phone_number){
        this.phone_number = phone_number;
    }
}
