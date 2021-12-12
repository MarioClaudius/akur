package com.akur.akurandroid;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://arcane-brushlands-61192.herokuapp.com/")
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    //baseUrl => http://10.0.2.2:210 buat coba di emulator yang konek ke database lokal
    //        => http://192.168.1.100:210 buat coba di hp yang terkonek ke wifi (pake IP desktop)
}
