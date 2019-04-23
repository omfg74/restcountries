package com.example.restcountries.fortest;

import com.example.restcountries.network.RetrofitClient;

import okhttp3.OkHttpClient;

public class OkhttpProviderForTesting extends RetrofitClient {
    public static OkHttpClient getOkhttp(){
        return RetrofitClient.getOkhttp();
    }
}
