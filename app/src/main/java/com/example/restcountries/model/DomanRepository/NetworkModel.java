package com.example.restcountries.model.DomanRepository;

import com.example.restcountries.contract.MainActivityContract;
import com.example.restcountries.model.county.Country;
import com.example.restcountries.network.RetrofitClient;
import com.example.restcountries.network.RetrofitInterface;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class NetworkModel implements MainActivityContract.Model.LoadCountryInterface {
    private RetrofitInterface retrofitInterface;

    @Override
    public Observable<Country> loadCountry() {
        retrofitInterface = RetrofitClient.getInstance().create(RetrofitInterface.class);

        return retrofitInterface
                .getAllCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                })
                .flatMap(new Function<List<Country>, ObservableSource<Country>>() {
                    @Override
                    public ObservableSource<Country> apply(List<Country> countries) throws Exception {
                        return Observable.fromIterable(countries).subscribeOn(Schedulers.io());
                    }
                });
    }
}
