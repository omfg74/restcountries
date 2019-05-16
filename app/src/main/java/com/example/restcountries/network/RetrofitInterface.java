package com.example.restcountries.network;

import com.example.restcountries.model.country.Country;

import io.reactivex.Observable;

import java.util.List;


import retrofit2.http.GET;

public interface RetrofitInterface {
    @GET("rest/v2/all")
    Observable<List<Country>> getAllCountries();
}
