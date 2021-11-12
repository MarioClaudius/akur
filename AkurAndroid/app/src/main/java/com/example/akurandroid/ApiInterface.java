package com.example.akurandroid;

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
    @GET("/show")
    Call<List<AkurAccount>> getAkurAccount();

    @FormUrlEncoded
    @POST("/insert")
    Call<AkurAccount> createAkurAccount(@Field("username") String username,
                                        @Field("email") String email,
                                        @Field("password") String password);

    @FormUrlEncoded
    @POST("/update")
    Call<Boolean> updateAccountPassword(@Field("username") String username,
                                        @Field("oldPassword") String oldPassword,
                                        @Field("newPassword") String newPassword);
}
