package com.example.akurandroid;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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
    Call<List<ScanHistory>> getHistoryList(@Field("user_id") int id);
}
