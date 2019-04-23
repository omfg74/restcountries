package com.example.restcountries.network;

import com.example.restcountries.model.county.Country;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface RetrofitInterface {
    @GET("rest/v2/all")
    Single<List<Country>> getAllCountries();
}
