package com.example.restcountries.network;

import android.graphics.Bitmap;

import com.example.restcountries.model.county.Country;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface RetrofitInterface {
    @GET("rest/v2/all")
    Single<List<Country>> getAllCountries();

    @GET//THis was the simliest way to use. It can be done also using regex or smth else
    Single<Bitmap> getFlag(@Url() String flag);
}
