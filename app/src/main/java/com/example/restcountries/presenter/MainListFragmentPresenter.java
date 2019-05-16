package com.example.restcountries.presenter;

import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;

import com.bumptech.glide.RequestBuilder;
import com.example.restcountries.Constants;
import com.example.restcountries.contract.MainListFragmentContract;
import com.example.restcountries.model.country.Country;
import com.example.restcountries.model.realm.RealmCountry;


import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainListFragmentPresenter implements MainListFragmentContract.Presenter {
    MainListFragmentContract.View view;
    MainListFragmentContract.Model model;
    RequestBuilder<PictureDrawable> requestBuilder;
    ArrayList<Country> countries = new ArrayList<>();
    HashMap<String, PictureDrawable>pictureDrawableHashMap;

    public MainListFragmentPresenter(MainListFragmentContract.View view) {
        this.view = view;
        this.model = new RealmCountry();
    }

    @Override
    public void onCreate(Bundle bundle) {
        pictureDrawableHashMap = (HashMap<String, PictureDrawable>) bundle.getSerializable(Constants.COUNTRIES_FLAG);
        countries = (ArrayList<Country>) bundle.getSerializable(Constants.COUNTRIES_KEY);
        loadPictures(countries, pictureDrawableHashMap);
    }

    @Override
    public void onSaveInstanceState( Bundle outState) {
        outState.putSerializable("countries",countries);
        outState.putSerializable("hashmap",pictureDrawableHashMap);
    }

    private void loadPictures(ArrayList<Country> countries,  HashMap<String, PictureDrawable>pictureDrawableHashMap) {
        Observable.fromIterable(countries)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Country>() {
                    @Override
                    public void accept(Country country) throws Exception {
                        view.postDataToList(country, pictureDrawableHashMap.get(country.getFlag()));
                    }
                });
    }

}
