package com.example.restcountries;

import com.example.restcountries.fortest.OkhttpProviderForTesting;
import com.example.restcountries.network.RetrofitClient;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Assert.*;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RetrofitTest {
    @Before
   public void init(){

    }
    @Test
    public void testRetrofit(){
        Retrofit retrofit = RetrofitClient.getInstance();
        assertNotNull(retrofit);
    }
    @Test
    public void testClient(){
        OkHttpClient okHttpClient = OkhttpProviderForTesting.getOkhttp();
        assertNotNull(okHttpClient);
    }

}
