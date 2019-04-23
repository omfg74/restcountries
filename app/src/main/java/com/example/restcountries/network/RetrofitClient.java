package com.example.restcountries.network;

import com.example.restcountries.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit =null;
    private static OkHttpClient okHttpClient;


    public static Retrofit getInstance(){
        if(okHttpClient==null){
            getOkhttp();
        }
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASEURL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

        }
        return retrofit;
    }

    private static OkHttpClient getOkhttp() {
        OkHttpClient okhttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(addInterceptor()).build();
        return okhttpClient;
    }

    private static Interceptor addInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        return httpLoggingInterceptor;
    }
}
