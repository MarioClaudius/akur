package com.example.akurandroid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("/show")
    Call<List<AkurAccount>> getAkurAccount();
}
