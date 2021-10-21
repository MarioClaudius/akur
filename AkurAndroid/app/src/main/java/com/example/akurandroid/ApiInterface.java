package com.example.akurandroid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("/show")
    Call<List<AkurAccount>> getAkurAccount();

    @FormUrlEncoded
    @POST("/insert")
    Call<AkurAccount> createAkurAccount(@Field("username") String username, @Field("email") String email, @Field("password") String password);
}
