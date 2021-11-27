package com.example.akurandroid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("/account/show")
    Call<List<AkurAccount>> getAkurAccount();

    @FormUrlEncoded
    @POST("/account/login")
    Call<AkurAccount> getAkurAccountId(@Field("username") String username,
                                   @Field("password") String password);

    @FormUrlEncoded
    @POST("/account/register")
    Call<AkurAccount> createAkurAccount(@Field("username") String username,
                                        @Field("email") String email,
                                        @Field("password") String password);

    @FormUrlEncoded
    @POST("/account/userinfo")
    Call<AkurAccount> getAkurAccountInfo(@Field("user_id") int id);

    @FormUrlEncoded
    @POST("/account/updateinfo")
    Call<Boolean> updateAkurAccountInfo(@Field("user_id") int id,
                                        @Field("nama_toko") String storeName,
                                        @Field("phone_number") String phoneNumber);

    @FormUrlEncoded
    @POST("/account/changepassword")
    Call<Boolean> updateAccountPassword(@Field("user_id") int id,
                                        @Field("oldPassword") String oldPassword,
                                        @Field("newPassword") String newPassword);

    @FormUrlEncoded
    @POST("/account/history")
    Call<List<Scan>> getHistoryList(@Field("user_id") int id);

    @GET("/account/formatresi")
    Call<List<ScanFormat>> getFormatList();

    @FormUrlEncoded
    @POST("/account/scanresi")
    Call<Boolean> insertScan(@Field("user_id") int id, @Field("nama_kurir") String courierName, @Field("no_resi") String receiptNumber);

    @FormUrlEncoded
    @POST("/account/datakurir")
    Call<List<Scan>> getCourierData(@Field("user_id") int id, @Field("nama_kurir") String courierName);

    @FormUrlEncoded
    @POST("/account/apiinfo")
    Call<Scan> getScanDetails(@Field("id_qr") int id);
}
