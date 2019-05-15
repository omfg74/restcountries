package com.example.restcountries.presenter;

import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;

import com.bumptech.glide.RequestBuilder;
import com.example.restcountries.Constants;
import com.example.restcountries.contract.MainListFragmentContract;
import com.example.restcountries.model.county.Country;
import com.example.restcountries.model.realm.RealmCountry;
import com.example.restcountries.model.realm.RealmCurrency;


import java.nio.Buffer;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainListFragmentPresenter implements MainListFragmentContract.Presenter {
    MainListFragmentContract.View view;
    MainListFragmentContract.Model model;
    RequestBuilder<PictureDrawable> requestBuilder;

    public MainListFragmentPresenter(MainListFragmentContract.View view) {
        this.view = view;
        this.model = new RealmCountry();

    }

    @Override
    public void onCreate(Bundle bundle) {
        ArrayList<Country> countries = new ArrayList<>();
        countries = (ArrayList<Country>) bundle.getSerializable(Constants.COUNTRIES_KEY);
        loadPictures(countries);
    }

    private void loadPictures(ArrayList<Country> countries) {
        PictureDrawable pictureDrawable;
        Observable.fromIterable(countries)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Country>() {
                    @Override
                    public void accept(Country country) throws Exception {
                        view.postDataToList(country);
                    }
                });
    }

}
